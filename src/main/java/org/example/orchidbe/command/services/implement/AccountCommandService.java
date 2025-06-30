package org.example.orchidbe.command.services.implement;

import org.example.orchidbe.command.entities.AccountEntity;
import org.example.orchidbe.command.entities.OrderEntity;
import org.example.orchidbe.command.entities.RoleEntity;
import org.example.orchidbe.command.repositories.AccountRepository;
import org.example.orchidbe.command.repositories.RoleRepository;
import org.example.orchidbe.command.services.define.IAccountCommandService;
import org.example.orchidbe.events.account.AccountBlockedEvent;
import org.example.orchidbe.events.account.AccountCreatedEvent;
import org.example.orchidbe.events.account.AccountUnblockedEvent;
import org.example.orchidbe.events.account.AccountUpdatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class AccountCommandService implements IAccountCommandService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void createAccount(String username, String password, String email) {
        password = hashMD5(password);
        AccountEntity accountEntity = new AccountEntity(username, email, password);
        RoleEntity role = roleRepository.findByRoleName("Customer")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        accountEntity.setRoleEntity(role);
        accountEntity.setAvailable(true);
        AccountEntity savedAccount = accountRepository.save(accountEntity);

        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setId(savedAccount.getId());
        event.setUserName(savedAccount.getUserName());
        event.setEmail(savedAccount.getEmail());
        event.setRoleName(savedAccount.getRoleEntity().getRoleName());
        event.setOrderIds(new HashSet<>());
        event.setAvailable(savedAccount.isAvailable());

        rabbitTemplate.convertAndSend("accountExchange", "account.created", event);
    }

    @Override
    @Transactional
    public void updateAccount(Long accountId, String username, String currentPassword, String newPassword, String email) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (username != null && !username.isEmpty()) {
            accountEntity.setUserName(username);
        }
        if (email != null && !email.isEmpty()) {
            accountEntity.setEmail(email);
        }
        if (newPassword != null && !newPassword.isEmpty() && currentPassword != null && !currentPassword.isEmpty()) {
            String hashedCurrentPassword = hashMD5(currentPassword);
            if (accountEntity.getPassword().equals(hashedCurrentPassword)) {
                accountEntity.setPassword(hashMD5(newPassword));
            } else {
                throw new RuntimeException("Current password is incorrect");
            }
        }

        AccountEntity updatedAccount = accountRepository.save(accountEntity);

        AccountUpdatedEvent event = new AccountUpdatedEvent();
        event.setId(updatedAccount.getId());
        event.setUserName(updatedAccount.getUserName());
        event.setEmail(updatedAccount.getEmail());
        event.setRoleName(updatedAccount.getRoleEntity().getRoleName());
        event.setOrderIds(updatedAccount.getOrderEntities() != null
                ? updatedAccount.getOrderEntities().stream().map(OrderEntity::getOrderId).collect(Collectors.toSet())
                : new HashSet<>());
        event.setAvailable(updatedAccount.isAvailable());

        rabbitTemplate.convertAndSend("accountExchange", "account.updated", event);
    }

    @Override
    @Transactional
    public void blockAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountEntity.setAvailable(false);
        AccountEntity updatedAccount = accountRepository.save(accountEntity);

        // Tạo sự kiện AccountBlockedEvent
        AccountBlockedEvent event = new AccountBlockedEvent();
        event.setId(updatedAccount.getId());
        event.setAvailable(updatedAccount.isAvailable());

        // Gửi sự kiện qua RabbitMQ
        rabbitTemplate.convertAndSend("accountExchange", "account.blocked", event);
    }

    @Override
    @Transactional
    public void unblockAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountEntity.setAvailable(true);
        AccountEntity updatedAccount = accountRepository.save(accountEntity);

        // Tạo sự kiện AccountUnblockedEvent
        AccountUnblockedEvent event = new AccountUnblockedEvent();
        event.setId(updatedAccount.getId());
        event.setAvailable(updatedAccount.isAvailable());

        // Gửi sự kiện qua RabbitMQ
        rabbitTemplate.convertAndSend("accountExchange", "account.unblocked", event);
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