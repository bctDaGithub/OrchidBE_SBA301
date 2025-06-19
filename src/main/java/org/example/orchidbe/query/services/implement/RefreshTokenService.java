package org.example.orchidbe.query.services.implement;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {
    private final Map<Long, String> refreshTokenStore = new ConcurrentHashMap<>();

    public void save(Long userId, String refreshToken) {
        refreshTokenStore.put(userId, refreshToken);
    }

    public boolean isValid(Long userId, String token) {
        return token.equals(refreshTokenStore.get(userId));
    }
}