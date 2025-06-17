package org.example.orchidbe.query.services.implement;

import org.example.orchidbe.query.documents.OrchidDocument;
import org.example.orchidbe.query.repositories.OrchidMongoRepository;
import org.example.orchidbe.query.services.define.IOrchidQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrchidQueryService implements IOrchidQueryService {

    @Autowired
    private  OrchidMongoRepository orchidMongoRepository;

    @Override
    public List<OrchidDocument> findAll() {
        return orchidMongoRepository.findAll();
    }

    @Override
    public OrchidDocument findByOrchidId(Long id) {
        return orchidMongoRepository.findByOrchidId(id);
    }
}
