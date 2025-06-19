package org.example.orchidbe.command.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrchidEntityDTO {
    private Long orchidId;
    private String orchidName;
    private String orchidDescription;
    private double price;
    private String orchidUrl;
    private boolean isNatural;
    private boolean isAvailable;
}
