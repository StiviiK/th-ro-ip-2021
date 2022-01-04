package de.throsenheim.ip.spm.repository;

import de.throsenheim.ip.spm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getByUsername(String username);
}
