package org.example.orchidbe.query.repositories;

import org.example.orchidbe.query.documents.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import  java.util.List;
import java.util.Optional;

public interface OrderMongoRepository extends MongoRepository<OrderDocument, Long> {
    List<OrderDocument> findByAccountId(Long accountId);
}
