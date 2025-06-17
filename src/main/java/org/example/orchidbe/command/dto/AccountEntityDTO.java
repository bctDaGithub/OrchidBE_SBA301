package org.example.orchidbe.command.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.orchidbe.command.entities.OrderEntity;

import java.util.List;

@Data
@Getter
@Setter
public class AccountEntityDTO {
    private long id;
    private String userName;
    private String email;
    private String currentPassword;
    private String newPassword;
    private boolean isAvailable;

    public AccountEntityDTO() {
    }

    public AccountEntityDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.currentPassword = password;
    }
}
