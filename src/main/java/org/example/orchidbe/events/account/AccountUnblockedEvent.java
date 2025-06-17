package org.example.orchidbe.events.account;

import lombok.Data;

@Data
public class AccountUnblockedEvent {
    private Long id;
    private boolean isAvailable;
}