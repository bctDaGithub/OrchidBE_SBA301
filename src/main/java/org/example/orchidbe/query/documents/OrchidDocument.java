package org.example.orchidbe.query.documents;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orchids")
@Data
public class OrchidDocument {
    @Id
    private Long orchidId;
    private String orchidName;
    private String orchidDescription;
    private double price;
    private String orchidUrl;
    private boolean isNatural;
    private boolean isAvailable;

}
