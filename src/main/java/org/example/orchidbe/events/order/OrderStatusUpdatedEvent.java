package org.example.orchidbe.events.order;

import lombok.Data;

@Data
public class OrderStatusUpdatedEvent {
    private Long orderId;
    private String status;
}
