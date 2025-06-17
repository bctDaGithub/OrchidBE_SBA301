package org.example.orchidbe.query.services.implement;

import org.example.orchidbe.query.documents.OrderDocument;
import org.example.orchidbe.query.repositories.OrderMongoRepository;
import org.example.orchidbe.query.services.define.IOrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryService implements IOrderQueryService {

    private final OrderMongoRepository orderMongoRepository;

    @Autowired
    public OrderQueryService(OrderMongoRepository orderMongoRepository) {
        this.orderMongoRepository = orderMongoRepository;
    }

    public Optional<OrderDocument> getOrderById(Long orderId) {
        return orderMongoRepository.findById(orderId);
    }

    public List<OrderDocument> getOrdersByAccountId(Long accountId) {
        return orderMongoRepository.findByAccountId(accountId);
    }

    public List<OrderDocument> getAllOrders() {
        return orderMongoRepository.findAll();
    }
}
