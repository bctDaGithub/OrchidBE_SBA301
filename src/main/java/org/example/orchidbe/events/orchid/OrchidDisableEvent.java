package org.example.orchidbe.events.orchid;

import lombok.Data;

@Data
public class OrchidDisableEvent {
        private Long orchidId;
        private boolean isAvailable;
}
