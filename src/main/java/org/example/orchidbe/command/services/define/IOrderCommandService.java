package org.example.orchidbe.command.services.define;

import org.example.orchidbe.command.dtos.CreateOrderRequestDTO;

public interface IOrderCommandService {
    public void createOrder(CreateOrderRequestDTO request);
    public void updateOrderStatus(Long orderId, String newStatus);
}