package org.example.orchidbe.command.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name= "accounts")
@Data
public class Account {
    @Id
    private long id;
    private String accountName;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "account")
    private Set<Order> orders;

    private boolean isAvailable;
    public Account() {
    }


}
