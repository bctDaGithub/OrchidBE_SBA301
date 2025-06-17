package org.example.orchidbe.listeners;

import org.example.orchidbe.events.order.OrderCreatedEvent;
import org.example.orchidbe.events.order.OrderStatusUpdatedEvent;
import org.example.orchidbe.query.documents.OrderDocument;
import org.example.orchidbe.query.repositories.OrderMongoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEventListener {

    private final OrderMongoRepository orderMongoRepository;

    @Autowired
    public OrderEventListener(OrderMongoRepository orderMongoRepository) {
        this.orderMongoRepository = orderMongoRepository;
    }

    @RabbitListener(queues = "orderCreatedQueue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        OrderDocument document = new OrderDocument();
        document.setId(event.getId());
        document.setAccountId(event.getAccountId());

        // Ánh xạ orderDetails từ event sang document
        List<OrderDocument.OrderDetailDocument> orderDetails = event.getOrderDetails().stream()
                .map(eventDetail -> {
                    OrderDocument.OrderDetailDocument detailDoc = new OrderDocument.OrderDetailDocument();
                    detailDoc.setOrchidId(eventDetail.getOrchidId());
                    detailDoc.setOrchidName(eventDetail.getOrchidName());
                    detailDoc.setUnitPrice(eventDetail.getUnitPrice());
                    detailDoc.setQuantity(eventDetail.getQuantity());
                    return detailDoc;
                })
                .collect(Collectors.toList());
        document.setOrderDetails(orderDetails);

        // Mặc định status khi tạo mới (có thể điều chỉnh theo logic nghiệp vụ)
        document.setStatus("PENDING");

        orderMongoRepository.save(document);
    }

    @RabbitListener(queues = "orderStatusUpdatedQueue")
    public void handleOrderStatusUpdated(OrderStatusUpdatedEvent event) {
        orderMongoRepository.findById(event.getOrderId()).ifPresent(document -> {
            document.setStatus(event.getStatus());
            orderMongoRepository.save(document);
        });
    }
}