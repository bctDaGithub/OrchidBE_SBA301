package org.example.orchidbe.query.services.implement;

import org.example.orchidbe.command.entities.AccountEntity;
import org.example.orchidbe.command.repositories.AccountRepository;
import org.example.orchidbe.jwt.JwtUtil;
import org.example.orchidbe.query.dtos.LoginRequest;
import org.example.orchidbe.query.dtos.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class AuthQueryService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) throws Exception {
        AccountEntity user = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("User not found"));

        String hashedInputPassword = hashMD5(request.getPassword());

        if (!user.getPassword().equals(hashedInputPassword)) {
            throw new Exception("Invalid password");
        }

        String accessToken = jwtUtil.generateAccessToken(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getRoleEntity().getRoleName()
        );

        String refreshToken = jwtUtil.generateRefreshToken(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getRoleEntity().getRoleName()
        );

        refreshTokenService.save(user.getId(), refreshToken);
        return new LoginResponse(accessToken, refreshToken);
    }

    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}