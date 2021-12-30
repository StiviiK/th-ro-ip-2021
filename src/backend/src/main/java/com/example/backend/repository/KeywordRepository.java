package com.example.backend.repository;

import com.example.backend.models.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
}
