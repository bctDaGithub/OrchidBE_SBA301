package org.example.orchidbe.listeners;

import org.example.orchidbe.events.orchid.OrchidCreatedEvent;
import org.example.orchidbe.events.orchid.OrchidDisableEvent;
import org.example.orchidbe.events.orchid.OrchidEnableEvent;
import org.example.orchidbe.events.orchid.OrchidUpdatedEvent;
import org.example.orchidbe.query.documents.OrchidDocument;
import org.example.orchidbe.query.repositories.AccountMongoRepository;
import org.example.orchidbe.query.repositories.OrchidMongoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrchidEventListener {

    @Autowired
    private final OrchidMongoRepository orchidMongoRepository;

    public OrchidEventListener(OrchidMongoRepository orchidMongoRepository) {
        this.orchidMongoRepository = orchidMongoRepository;
    }

    @RabbitListener(queues = "orchidCreatedQueue")
    public void handleOrchidCreated(OrchidCreatedEvent event) {
        OrchidDocument orchidDocument = new OrchidDocument();
        orchidDocument.setOrchidId(event.getOrchidId());
        orchidDocument.setOrchidName(event.getOrchidName());
        orchidDocument.setOrchidDescription(event.getOrchidDescription());
        orchidDocument.setPrice(event.getPrice());
        orchidDocument.setOrchidUrl(event.getOrchidUrl());
        orchidDocument.setNatural(event.isNatural());
        orchidDocument.setAvailable(true);
        orchidMongoRepository.save(orchidDocument);
    }

    @RabbitListener(queues = "orchidUpdatedQueue")
    public void handleOrchidUpdated(OrchidUpdatedEvent event){
        OrchidDocument orchid = orchidMongoRepository.findByOrchidId(event.getOrchidId());
        orchid.setOrchidName(event.getOrchidName());
        orchid.setOrchidDescription(event.getOrchidDescription());
        orchid.setPrice(event.getPrice());
        orchid.setOrchidUrl(event.getOrchidUrl());
        orchid.setNatural(event.isNatural());
        orchid.setAvailable(event.isAvailable());
        orchidMongoRepository.save(orchid);
    }

    @RabbitListener(queues = "orchidDisableQueue")
    public void handleOrchidDisable (OrchidDisableEvent event) {
        orchidMongoRepository.findByOrchidId(event.getOrchidId()).setAvailable(false);
        orchidMongoRepository.save(orchidMongoRepository.findByOrchidId(event.getOrchidId()));
    }

    @RabbitListener(queues = "orchidEnableQueue")
    public void handleOrchidEnable (OrchidEnableEvent event) {
        orchidMongoRepository.findByOrchidId(event.getOrchidId()).setAvailable(true);
        orchidMongoRepository.save(orchidMongoRepository.findByOrchidId(event.getOrchidId()));
    }
}
