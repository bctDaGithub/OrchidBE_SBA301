package org.example.orchidbe.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.orchidbe.command.entities.AccountEntity;
import org.example.orchidbe.command.entities.RoleEntity;
import org.example.orchidbe.command.repositories.AccountRepository;
import org.example.orchidbe.command.repositories.RoleRepository;
import org.example.orchidbe.events.account.AccountCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final RabbitTemplate rabbitTemplate;

    private final String DEFAULT_ADMIN_USERNAME = "admin";
    private final String DEFAULT_ADMIN_PASSWORD = "admin123";
    private final String DEFAULT_ADMIN_EMAIL = "admin@orchid.com";

    @PostConstruct
    @Transactional
    public void initAdminAccount() {
        boolean adminExists = accountRepository.findByEmail(DEFAULT_ADMIN_EMAIL).isPresent();
        if (adminExists) {
            System.out.println("ℹ️ Admin account already exists.");
            return;
        }

        RoleEntity adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new RuntimeException("⚠️ Role 'Admin' not found. Please seed roles first."));

        AccountEntity admin = new AccountEntity();
        admin.setUserName(DEFAULT_ADMIN_USERNAME);
        admin.setEmail(DEFAULT_ADMIN_EMAIL);
        admin.setPassword(hashMD5(DEFAULT_ADMIN_PASSWORD));
        admin.setRoleEntity(adminRole);
        admin.setAvailable(true);

        AccountEntity savedAdmin = accountRepository.save(admin);

        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setId(savedAdmin.getId());
        event.setUserName(savedAdmin.getUserName());
        event.setEmail(savedAdmin.getEmail());
        event.setRoleName(savedAdmin.getRoleEntity().getRoleName());
        event.setOrderIds(new HashSet<>());
        event.setAvailable(savedAdmin.isAvailable());

        rabbitTemplate.convertAndSend("accountExchange", "account.created", event);

        System.out.println("✅ Admin account created and synced to Mongo.");
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
