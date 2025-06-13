package org.example.orchidbe.command.repository;

import org.example.orchidbe.command.entities.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrchidRepository extends JpaRepository<Orchid,Long> {
}
