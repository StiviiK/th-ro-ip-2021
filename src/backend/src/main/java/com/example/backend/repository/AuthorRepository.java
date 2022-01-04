package com.example.backend.repository;

import com.example.backend.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories that stores all authors that were found in different papers.
 *
 * @author Lukas Metzner
 */
public interface AuthorRepository extends JpaRepository<Author, String> {
}
