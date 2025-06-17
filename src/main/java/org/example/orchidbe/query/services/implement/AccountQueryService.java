package org.example.orchidbe.query.services.implement;

import org.example.orchidbe.query.documents.AccountDocument;
import org.example.orchidbe.query.repositories.AccountMongoRepository;
import org.example.orchidbe.query.services.define.IAccountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryService implements IAccountQueryService {

    @Autowired
    private final AccountMongoRepository accountMongoRepository;

    public AccountQueryService(AccountMongoRepository accountMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
    }

    @Override
    public Optional<AccountDocument> findById(Long id) {
        return accountMongoRepository.findById(id);
    }

    @Override
    public List<AccountDocument> findAll() {
        return accountMongoRepository.findAll();
    }
}