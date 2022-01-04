package com.example.backend.repository;

import com.example.backend.models.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories that stores all keywords that were found in different papers.
 *
 * @author Lukas Metzner
 */
public interface KeywordRepository extends JpaRepository<Keyword, String> {
}
