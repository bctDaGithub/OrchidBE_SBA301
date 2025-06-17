package org.example.orchidbe.command.repositories;

import org.example.orchidbe.command.entities.OrchidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrchidRepository extends JpaRepository<OrchidEntity,Long> {
}
