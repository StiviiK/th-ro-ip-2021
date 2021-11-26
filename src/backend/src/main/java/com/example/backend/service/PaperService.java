package com.example.backend.service;

import com.example.backend.exceptions.PaperNotFoundException;
import com.example.backend.models.Paper;
import com.example.backend.repository.PaperRepository;
import com.example.backend.util.KeywordsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

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

    public Paper addPaper(Paper paper, String username) throws NullPointerException, InterruptedException, IOException, URISyntaxException {
        var user = myUserDetailsService.loadUserByUsername(username);
        if (paper == null) {
            throw new NullPointerException("Paper was null");
        }
        //TODO get paper text before keyword extraction
        var keywordsResponse = KeywordsApi.getKeywords(paper.getText());
        paper.setKeywords(KeywordsApi.extractKeywords(keywordsResponse));
        paper = paperRepository.save(paper);
        //TODO add paper to user
        return paper;
    }
}
