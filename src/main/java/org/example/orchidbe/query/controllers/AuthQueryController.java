package org.example.orchidbe.query.controllers;

import org.example.orchidbe.command.entities.AccountEntity;
import org.example.orchidbe.command.repositories.AccountRepository;
import org.example.orchidbe.jwt.JwtUtil;
import org.example.orchidbe.query.dtos.LoginRequest;
import org.example.orchidbe.query.dtos.LoginResponse;
import org.example.orchidbe.query.services.implement.AuthQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.query-path}/auth")
public class AuthQueryController {

    @Autowired
    private AuthQueryService authService;

    @Autowired
    private  AccountRepository accountRepository;

    private final JwtUtil jwtUtil;

    public AuthQueryController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);

        AccountEntity user = null;
        try {
            user = accountRepository.findByEmail(username)
                    .orElseThrow(() -> new Exception("User not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getUserName(), user.getEmail(), user.getRoleEntity().getRoleName());

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
