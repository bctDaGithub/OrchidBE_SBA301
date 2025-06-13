package org.example.orchidbe.command.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "orchids")
@Data
public class Orchid {
    @Id
    private int orchidId;
    private String orchidName;
    private String orchidDescription;
    private double price;
    private String orchidUrl;
    private boolean isNatural;
    private boolean isAvailable;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}
