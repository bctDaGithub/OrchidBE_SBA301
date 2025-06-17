package org.example.orchidbe.query.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.List;

@Document(collection = "orders")
@Data
@Getter
@Setter
public class OrderDocument {
    @Id
    private Long id;
    private Long accountId;
    private List<OrderDetailDocument> orderDetails;
    private String status;

    @Data
    public static class OrderDetailDocument {
        private Long orchidId;
        private String orchidName;
        private double unitPrice;
        private int quantity;
    }
}