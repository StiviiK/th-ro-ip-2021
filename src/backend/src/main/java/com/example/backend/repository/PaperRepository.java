package com.example.backend.repository;

import com.example.backend.models.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaperRepository extends JpaRepository<Paper, UUID> {
}
