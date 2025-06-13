package org.example.orchidbe.listeners;

import org.example.orchidbe.events.AccountCreatedEvent;
import org.example.orchidbe.query.documents.AccountDocument;
import org.example.orchidbe.query.repositories.AccountMongoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {
    private final AccountMongoRepository accountMongoRepository;

    public AccountEventListener(AccountMongoRepository accountMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
    }

    @RabbitListener(queues = "accountCreatedQueue")
    public void handleAccountCreated(AccountCreatedEvent event) {
        AccountDocument document = new AccountDocument();
        document.setId(event.getId());
        document.setUserName(event.getUserName());
        document.setEmail(event.getEmail());
        document.setRole(new AccountDocument.RoleDocument());
        document.getRole().setName(event.getRoleName());
        document.setOrderIds(event.getOrderIds());
        document.setAvailable(event.isAvailable());

        accountMongoRepository.save(document);
    }
}