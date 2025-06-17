package org.example.orchidbe.events.orchid;

import lombok.Data;

@Data
public class OrchidUpdatedEvent {
    private Long orchidId;
    private String orchidName;
    private String orchidDescription;
    private double price;
    private String orchidUrl;
    private boolean isNatural;
    private boolean isAvailable;
}
