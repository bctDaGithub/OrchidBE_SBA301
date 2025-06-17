package org.example.orchidbe.command.services.define;

import org.example.orchidbe.command.dto.CreateOrderRequestDTO;
import org.example.orchidbe.command.dto.OrderItem;

import java.util.List;

public interface IOrderCommandService {
    public void createOrder(CreateOrderRequestDTO request);
    public void updateOrderStatus(Long orderId, String newStatus);
}