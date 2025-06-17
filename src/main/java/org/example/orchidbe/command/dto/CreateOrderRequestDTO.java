package org.example.orchidbe.command.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDTO {
    private Long accountId;
    private List<Item> items;

    @Data
    public static class Item {
        private Long orchidId;
        private int quantity;
    }
}