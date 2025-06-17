package org.example.orchidbe.query.services.define;

import org.example.orchidbe.query.documents.OrderDocument;
import org.example.orchidbe.query.repositories.OrderMongoRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderQueryService {
    public Optional<OrderDocument> getOrderById(Long orderId);
    public List<OrderDocument> getOrdersByAccountId(Long accountId);
    public List<OrderDocument> getAllOrders();
}
