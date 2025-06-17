package org.example.orchidbe.query.repositories;

import org.example.orchidbe.query.documents.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountMongoRepository extends MongoRepository<AccountDocument, Long> {
}