package org.example.orchidbe.command.services.implement;

import org.example.orchidbe.command.entities.OrchidEntity;
import org.example.orchidbe.command.repositories.OrchidRepository;
import org.example.orchidbe.command.services.define.IOrchidCommandService;
import org.example.orchidbe.events.orchid.OrchidCreatedEvent;
import org.example.orchidbe.events.orchid.OrchidDisableEvent;
import org.example.orchidbe.events.orchid.OrchidEnableEvent;
import org.example.orchidbe.events.orchid.OrchidUpdatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrchidCommandService implements IOrchidCommandService {

    @Autowired
    private OrchidRepository orchidRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void createOrchid(OrchidEntity orchidEntity) {

        orchidRepository.save(orchidEntity);

        OrchidCreatedEvent event = new OrchidCreatedEvent();
        event.setOrchidId(orchidEntity.getOrchidId());
        event.setOrchidDescription(orchidEntity.getOrchidDescription());
        event.setOrchidName(orchidEntity.getOrchidName());
        event.setOrchidUrl(orchidEntity.getOrchidUrl());
        event.setNatural(orchidEntity.isNatural());
        event.setPrice(orchidEntity.getPrice());
        event.setAvailable(true);

        rabbitTemplate.convertAndSend("orchidExchange", "orchid.created", event);
    }

    @Override
    @Transactional
    public void updateOrchid(Long orchidId, String orchidName, String orchidDescription, double price, String orchidUrl, boolean isNatural) {
        OrchidEntity orchidEntity = orchidRepository.findById(orchidId).orElse(null);
        if (orchidEntity != null) {
            if (orchidName != null && !orchidName.isEmpty())
                orchidEntity.setOrchidName(orchidName);
            if (orchidDescription != null && !orchidDescription.isEmpty())
                orchidEntity.setOrchidDescription(orchidDescription);
            if (price >= 0)
                orchidEntity.setPrice(price);
            if (orchidUrl != null && !orchidUrl.isEmpty())
                orchidEntity.setOrchidUrl(orchidUrl);
            orchidEntity.setNatural(isNatural);
            orchidRepository.save(orchidEntity);


            OrchidUpdatedEvent event = new OrchidUpdatedEvent();
            event.setOrchidId(orchidId);
            event.setOrchidName(orchidName);
            event.setOrchidDescription(orchidDescription);
            event.setPrice(price);
            event.setOrchidUrl(orchidUrl);
            event.setNatural(isNatural);
            event.setAvailable(orchidEntity.isAvailable());

            rabbitTemplate.convertAndSend("orchidExchange", "orchid.updated", event);
        }

    }

    @Override
    @Transactional
    public void disableOrchid(Long orchidId) {
        OrchidEntity orchidEntity = orchidRepository.findById(orchidId).orElse(null);
        if (orchidEntity != null) {
            orchidEntity.setAvailable(false);
            orchidRepository.save(orchidEntity);

            OrchidDisableEvent event = new OrchidDisableEvent();
            event.setOrchidId(orchidId);
            event.setAvailable(orchidEntity.isAvailable());

            rabbitTemplate.convertAndSend("orchidExchange", "orchid.disable", event);
        }
    }

    @Override
    @Transactional
    public void enableOrchid(Long orchidId) {
        OrchidEntity orchidEntity = orchidRepository.findById(orchidId).orElse(null);
        if (orchidEntity != null) {
            orchidEntity.setAvailable(true);
            orchidRepository.save(orchidEntity);

            OrchidEnableEvent event = new OrchidEnableEvent();
            event.setOrchidId(orchidId);
            event.setAvailable(orchidEntity.isAvailable());

            rabbitTemplate.convertAndSend("orchidExchange", "orchid.enable", event);
        }
    }
}
