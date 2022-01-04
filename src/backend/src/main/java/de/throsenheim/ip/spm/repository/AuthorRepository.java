package de.throsenheim.ip.spm.repository;

import de.throsenheim.ip.spm.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
