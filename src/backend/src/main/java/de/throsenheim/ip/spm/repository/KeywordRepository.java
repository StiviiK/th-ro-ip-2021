package de.throsenheim.ip.spm.repository;

import de.throsenheim.ip.spm.models.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories that stores all keywords that were found in different papers.
 *
 * @author Lukas Metzner
 */
public interface KeywordRepository extends JpaRepository<Keyword, String> {
}
