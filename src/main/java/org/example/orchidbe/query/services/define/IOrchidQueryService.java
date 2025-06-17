package org.example.orchidbe.query.services.define;

import org.example.orchidbe.query.documents.OrchidDocument;

import java.util.List;
import java.util.Optional;

public interface IOrchidQueryService {
    public List<OrchidDocument> findAll();
    public OrchidDocument findByOrchidId(Long id);
}
