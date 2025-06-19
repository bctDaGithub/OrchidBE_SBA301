package org.example.orchidbe.command.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
