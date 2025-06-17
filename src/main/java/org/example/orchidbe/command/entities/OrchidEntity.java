package org.example.orchidbe.command.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "orchids")
@Data
public class OrchidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orchidId;
    private String orchidName;
    private String orchidDescription;
    private double price;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String orchidUrl;
    private boolean isNatural;
    private boolean isAvailable;

    @OneToMany(mappedBy = "orchidEntity")
    private Set<OrderDetailEntity> orderDetailEntities;

    public OrchidEntity(String orchidName, String orchidDescription, double price, String orchidUrl, boolean natural)  {
        this.orchidName = orchidName;
        this.orchidDescription = orchidDescription;
        this.price = price;
        this.orchidUrl = orchidUrl;
        this.isNatural = natural;
        this.isAvailable = true;
    }

    public OrchidEntity() {

    }
}
