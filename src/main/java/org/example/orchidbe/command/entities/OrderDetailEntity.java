package org.example.orchidbe.command.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {
    @Id
    private int orderDetailId;
    private int quantity;
    private double price;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "orchid_id")
    private Orchid orchid;

    public OrderDetail() {
    }


}
