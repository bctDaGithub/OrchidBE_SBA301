package org.example.orchidbe.command.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderItem {
    private Long orchidId;
    private int quantity;
    private Long accountId;

    public OrderItem() {
    }
}