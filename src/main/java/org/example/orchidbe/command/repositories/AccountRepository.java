package org.example.orchidbe.command.repositories;

import org.example.orchidbe.command.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    Optional<AccountEntity> findByEmail(String email);
}
