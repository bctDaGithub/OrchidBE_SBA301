package org.example.orchidbe.command.controllers;

import org.example.orchidbe.command.dto.AccountEntityDTO;
import org.example.orchidbe.command.services.define.IAccountCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.command-path}/account")
public class AccountCommandController {

    @Autowired
    private IAccountCommandService accountCommandService;

    public AccountCommandController() {}

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountEntityDTO accountEntityDTO) {
        accountCommandService.createAccount(
                accountEntityDTO.getUserName(),
                accountEntityDTO.getCurrentPassword(),
                accountEntityDTO.getEmail()
        );
        return ResponseEntity.ok("Account created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateAccount(@RequestBody AccountEntityDTO accountEntityDTO) {
        accountCommandService.updateAccount(
                accountEntityDTO.getId(),
                accountEntityDTO.getUserName(),
                accountEntityDTO.getCurrentPassword(),
                accountEntityDTO.getNewPassword(),
                accountEntityDTO.getEmail()
        );
        return ResponseEntity.ok("Account updated successfully");
    }

    @PutMapping("/block")
    public ResponseEntity<String> blockAccount(@RequestBody AccountEntityDTO accountEntityDTO) {
        accountCommandService.blockAccount(accountEntityDTO.getId());
        return ResponseEntity.ok("Account blocked successfully");
    }

    @PutMapping("/unblock")
    public ResponseEntity<String> unblockAccount(@RequestBody AccountEntityDTO accountEntityDTO) {
        accountCommandService.unblockAccount(accountEntityDTO.getId());
        return ResponseEntity.ok("Account unblocked successfully");
    }
}
