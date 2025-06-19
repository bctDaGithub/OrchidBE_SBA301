package org.example.orchidbe.command.services.implement;

import org.example.orchidbe.command.dtos.CreateOrderRequestDTO;
import org.example.orchidbe.command.entities.AccountEntity;
import org.example.orchidbe.command.entities.OrchidEntity;
import org.example.orchidbe.command.entities.OrderDetailEntity;
import org.example.orchidbe.command.entities.OrderEntity;
import org.example.orchidbe.command.repositories.AccountRepository;
import org.example.orchidbe.command.repositories.OrchidRepository;
import org.example.orchidbe.command.repositories.OrderRepository;
import org.example.orchidbe.command.services.define.IOrderCommandService;
import org.example.orchidbe.events.account.AccountUpdatedEvent;
import org.example.orchidbe.events.order.OrderCreatedEvent;
import org.example.orchidbe.events.order.OrderStatusUpdatedEvent;
import org.example.orchidbe.query.documents.OrderDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderCommandService implements IOrderCommandService {

    private static final Logger logger = LoggerFactory.getLogger(OrderCommandService.class);

    private final OrderRepository orderRepository;
    private final OrchidRepository orchidRepository;
    private final AccountRepository accountRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderCommandService(
            OrderRepository orderRepository,
            OrchidRepository orchidRepository,
            AccountRepository accountRepository,
            RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.orchidRepository = orchidRepository;
        this.accountRepository = accountRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderRequestDTO request) {
        try {
            // Validate input
            if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
                throw new IllegalArgumentException("Order must contain at least one item");
            }
            Objects.requireNonNull(request.getAccountId(), "Account ID must not be null");

            // Kiểm tra Account
            AccountEntity account = accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found with ID: " + request.getAccountId()));
            logger.info("Found account with ID: {}", request.getAccountId());

            // Tạo OrderEntity
            OrderEntity order = new OrderEntity();
            order.setAccountEntity(account);
            order.setOrderDate(Timestamp.from(Instant.now()));
            order.setOrderStatus("PENDING");
            List<OrderDetailEntity> orderDetails = new ArrayList<>(); // Use List instead of Set
            double totalAmount = 0.0;

            // Tạo OrderDetailEntity
            for (CreateOrderRequestDTO.Item item : request.getItems()) {
                if (item.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than 0 for orchid ID: " + item.getOrchidId());
                }

                logger.info("Fetching orchid with ID: {}", item.getOrchidId());
                OrchidEntity orchid = orchidRepository.findById(item.getOrchidId())
                        .orElseThrow(() -> new RuntimeException("Orchid not found with ID: " + item.getOrchidId()));
                logger.info("Found orchid with ID: {}, Name: {}", item.getOrchidId(), orchid.getOrchidName());

                OrderDetailEntity detail = new OrderDetailEntity();
                detail.setOrder(order);
                detail.setOrchidEntity(orchid);
                detail.setQuantity(item.getQuantity());
                detail.setPrice(orchid.getPrice() * item.getQuantity());

                orderDetails.add(detail);
                totalAmount += detail.getPrice();
            }

            logger.info("Created {} order details", orderDetails.size());
            order.setOrderDetailEntities(new HashSet<>(orderDetails)); // Convert to Set after creation
            order.setTotalAmount(totalAmount);

            // Lưu order (tự động lưu orderDetails nhờ cascade)
            order = orderRepository.save(order);
            logger.info("Created order with ID: {}", order.getOrderId());

            // Tạo và gửi OrderCreatedEvent
            OrderCreatedEvent orderEvent = new OrderCreatedEvent();
            orderEvent.setId(order.getOrderId());
            orderEvent.setAccountId(order.getAccountEntity().getId());
            List<OrderDocument.OrderDetailDocument> eventDetails = order.getOrderDetailEntities().stream()
                    .map(detail -> {
                        OrderDocument.OrderDetailDocument eventDetail = new OrderDocument.OrderDetailDocument();
                        eventDetail.setOrchidId(detail.getOrchidEntity().getOrchidId());
                        eventDetail.setOrchidName(detail.getOrchidEntity().getOrchidName());
                        eventDetail.setUnitPrice(detail.getPrice() / detail.getQuantity());
                        eventDetail.setQuantity(detail.getQuantity());
                        logger.info("Mapped order detail for orchid ID: {}", detail.getOrchidEntity().getOrchidId());
                        return eventDetail;
                    })
                    .toList();
            orderEvent.setOrderDetails(eventDetails);
            rabbitTemplate.convertAndSend("orderExchange", "order.created", orderEvent);
            logger.info("Sent OrderCreatedEvent for order ID: {}", order.getOrderId());

            // Cập nhật orderIds trong AccountDocument
            AccountUpdatedEvent accountEvent = new AccountUpdatedEvent();
            accountEvent.setId(account.getId());
            accountEvent.setOrderIds(account.getOrderEntities().stream()
                    .map(OrderEntity::getOrderId)
                    .collect(Collectors.toSet()));
            accountEvent.setUserName(account.getUserName());
            accountEvent.setEmail(account.getEmail());
            accountEvent.setRoleName(account.getRoleEntity().getRoleName());
            accountEvent.setAvailable(account.isAvailable());
            rabbitTemplate.convertAndSend("accountExchange", "account.updated", accountEvent);
            logger.info("Sent AccountUpdatedEvent for account ID: {}", account.getId());
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create order", e);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        try {
            // Validate input
            Objects.requireNonNull(orderId, "Order ID must not be null");
            Objects.requireNonNull(newStatus, "Status must not be null");

            // Kiểm tra Order
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
            logger.info("Found order with ID: {}", orderId);

            // Kiểm tra trạng thái hợp lệ
            if (!isValidStatus(newStatus)) {
                throw new IllegalArgumentException("Invalid order status: " + newStatus);
            }

            // Cập nhật trạng thái
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
            logger.info("Updated status for order ID: {} to {}", orderId, newStatus);

            // Tạo và gửi OrderStatusUpdatedEvent
            OrderStatusUpdatedEvent event = new OrderStatusUpdatedEvent();
            event.setOrderId(orderId);
            event.setStatus(newStatus);
            rabbitTemplate.convertAndSend("orderExchange", "order.status.updated", event);
            logger.info("Sent OrderStatusUpdatedEvent for order ID: {}", orderId);
        } catch (Exception e) {
            logger.error("Error updating order status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update order status", e);
        }
    }

    private boolean isValidStatus(String status) {
        return switch (status.toUpperCase()) {
            case "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED" -> true;
            default -> false;
        };
    }
}