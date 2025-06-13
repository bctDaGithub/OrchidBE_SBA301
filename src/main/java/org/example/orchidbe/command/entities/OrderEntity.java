package org.example.orchidbe.command.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.security.Timestamp;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Timestamp orderDate;
    private String orderStatus;
    private double totalAmount;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;


}
