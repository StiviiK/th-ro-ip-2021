package de.throsenheim.ip.spm.repository;

import de.throsenheim.ip.spm.models.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories that stores all papers.
 *
 * @author Alessandro Soro
 */
public interface PaperRepository extends JpaRepository<Paper, String> {
}
