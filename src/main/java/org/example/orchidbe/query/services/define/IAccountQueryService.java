package org.example.orchidbe.query.services.define;

import org.example.orchidbe.query.documents.AccountDocument;

import java.util.List;
import java.util.Optional;

public interface IAccountQueryService {
    Optional<AccountDocument> findById(Long id);
    List<AccountDocument> findAll();
}