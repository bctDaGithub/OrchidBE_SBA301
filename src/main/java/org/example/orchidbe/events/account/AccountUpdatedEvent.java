package org.example.orchidbe.events;

import lombok.Data;

import java.util.Set;

@Data
public class AccountUpdatedEvent {
    private Long id;
    private String userName;
    private String email;
    private String roleName;
    private Set<Long> orderIds;
    private boolean isAvailable;
}