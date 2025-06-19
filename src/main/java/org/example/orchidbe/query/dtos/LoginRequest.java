package org.example.orchidbe.query.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
