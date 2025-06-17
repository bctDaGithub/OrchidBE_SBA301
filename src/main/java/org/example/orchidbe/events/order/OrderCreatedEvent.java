package org.example.orchidbe.events.order;

import lombok.Data;
import org.example.orchidbe.query.documents.OrderDocument;
import java.util.List;

@Data
public class OrderCreatedEvent {
    private Long id;
    private Long accountId;
    private List<OrderDocument.OrderDetailDocument> orderDetails;

    @Data
    public static class OrderDetailDocument {
        private Long orchidId;
        private String orchidName;
        private double unitPrice;
        private int quantity;
    }
}
