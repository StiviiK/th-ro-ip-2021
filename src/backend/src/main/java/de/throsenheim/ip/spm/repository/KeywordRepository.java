package de.throsenheim.ip.spm.repository;

import de.throsenheim.ip.spm.models.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
}
