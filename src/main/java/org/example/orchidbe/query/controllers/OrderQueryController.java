package org.example.orchidbe.query.controllers;

import org.example.orchidbe.query.documents.OrderDocument;
import org.example.orchidbe.query.services.implement.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("${api.query-path}/order")
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    @Autowired
    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDocument> getOrderById(@PathVariable Long orderId) {
        return orderQueryService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<OrderDocument>> getOrdersByAccountId(@PathVariable Long accountId) {
        List<OrderDocument> orders = orderQueryService.getOrdersByAccountId(accountId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<OrderDocument>> getAllOrders() {
        return ResponseEntity.ok(orderQueryService.getAllOrders());
    }
}
