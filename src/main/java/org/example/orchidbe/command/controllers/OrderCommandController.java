package org.example.orchidbe.command.controllers;


import org.example.orchidbe.command.dtos.CreateOrderRequestDTO;
import org.example.orchidbe.command.services.define.IOrderCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("${api.command-path}/order")
public class OrderCommandController {

    @Autowired
    private IOrderCommandService orderCommandService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequestDTO request) {
        orderCommandService.createOrder(request);
        return ResponseEntity.ok("Order created successfully");
    }


    @PutMapping("{orderId}/{newStatus}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @PathVariable String newStatus) {
        orderCommandService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok("Order status updated successfully");
    }
}
