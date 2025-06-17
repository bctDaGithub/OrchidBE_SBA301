package org.example.orchidbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.example.orchidbe.query.repositories")
public class OrchidBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrchidBeApplication.class, args);
    }

}
