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
public class   AuthQueryService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) throws Exception {
        AccountEntity user = accountRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new Exception("User not found"));

//        String hashedInputPassword = hashMD5(request.getPassword());
        String hashedInputPassword = request.getPassword();

        if (!user.getPassword().equals(hashedInputPassword)) {
            throw new Exception("Invalid password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName());

        refreshTokenService.save(user.getId(), refreshToken);
        return new LoginResponse(accessToken, refreshToken);
    }

    private String hashMD5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(digest);
    }
}