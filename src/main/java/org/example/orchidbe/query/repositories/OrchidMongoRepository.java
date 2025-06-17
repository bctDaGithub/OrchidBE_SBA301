package org.example.orchidbe.query.repositories;

import org.example.orchidbe.query.documents.OrchidDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrchidMongoRepository extends MongoRepository<OrchidDocument, Long> {
    public OrchidDocument findByOrchidId(Long orchidId);
}
