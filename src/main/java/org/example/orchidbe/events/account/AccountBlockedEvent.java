package org.example.orchidbe.events.account;

import lombok.Data;

@Data
public class AccountBlockedEvent {
    private Long id;
    private boolean isAvailable;
}