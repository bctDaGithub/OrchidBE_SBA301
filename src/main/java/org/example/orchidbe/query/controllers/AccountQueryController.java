package org.example.orchidbe.query.controllers;

import org.example.orchidbe.query.documents.AccountDocument;
import org.example.orchidbe.query.services.define.IAccountQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.query-path}/account")
public class AccountQueryController {
    private final IAccountQueryService accountQueryService;

    public AccountQueryController(IAccountQueryService accountQueryService) {
        this.accountQueryService = accountQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDocument> getAccountById(@PathVariable Long id) {
        return accountQueryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AccountDocument>> getAllAccounts() {
        return ResponseEntity.ok(accountQueryService.findAll());
    }
}