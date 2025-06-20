package org.example.orchidbe.query.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
