package org.example.orchidbe.command.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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