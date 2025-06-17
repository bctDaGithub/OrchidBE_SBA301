package org.example.orchidbe.events.orchid;

import lombok.Data;

@Data
public class OrchidEnableEvent {
    private Long orchidId;
    private boolean isAvailable;
}
