package org.example.orchidbe.query.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.Set;

@Document(collection = "accounts") //
@Data
public class AccountDocument {
    @Id
    private Long id;
    private String userName;
    private String email;
    private RoleDocument role;
    private Set<Long> orderIds;
    private boolean isAvailable;

    @Data
    public static class RoleDocument {
        private String name;
    }
}