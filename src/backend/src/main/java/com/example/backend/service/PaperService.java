package com.example.backend.service;

import com.example.backend.exceptions.PaperNotFoundException;
import com.example.backend.models.Paper;
import com.example.backend.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public List<Paper> getPapers() {
        return paperRepository.findAll();
    }

    public Paper getPaper(UUID paperId) {
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        if (optionalPaper.isEmpty()) {
            throw new PaperNotFoundException(String.format("User with id: '%s' not found", paperId));
        }
        return optionalPaper.get();

    }
}
