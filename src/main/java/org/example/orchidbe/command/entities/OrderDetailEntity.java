package org.example.orchidbe.command.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "order_details")
@Getter
@Setter
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "orchid_id")
    private OrchidEntity orchidEntity;

    public OrderDetailEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailEntity that)) return false;
        return Objects.equals(order, that.order) &&
                Objects.equals(orchidEntity, that.orchidEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, orchidEntity);
    }

}