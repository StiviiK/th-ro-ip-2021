package com.example.backend.repository;

import com.example.backend.models.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories that stores all papers.
 *
 * @author Alessandro Soro
 */
public interface PaperRepository extends JpaRepository<Paper, String> {
}
