package com.example.backend.service;

import com.example.backend.exceptions.ArxivNotAvailableException;
import com.example.backend.exceptions.KeywordServiceNotAvailableException;
import com.example.backend.exceptions.PaperNotFoundException;
import com.example.backend.models.ArxivInformationResponse;
import com.example.backend.models.Author;
import com.example.backend.models.Keyword;
import com.example.backend.models.Paper;
import com.example.backend.repository.PaperRepository;
import com.example.backend.util.ArxivApi;
import com.example.backend.util.KeywordsApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private AuthorService authorService;

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

    public Paper addPaper(Paper paper, String username) throws KeywordServiceNotAvailableException, ArxivNotAvailableException {
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        if (paper == null) {
            throw new NullPointerException("Paper was null");
        }

        // Retrieve paper information
        ArxivInformationResponse arxivInformation = null;
        try {
            arxivInformation = ArxivApi.getArxivInformation(paper.getId());
        } catch (Exception exception) {
            throw new ArxivNotAvailableException("Arxiv.org API could not be reached.");
        }


        // Authors
        List<Author> authors = arxivInformation.getAuthors();

        // Title
        paper.setTitle(arxivInformation.getTitle());

        // Text
        // TODO change text to actual text not just summary
        paper.setText(arxivInformation.getSummary());

        // Extract Keywords
        // If keywords entered by user
        List<Keyword> keywords = new ArrayList<>();
        if (paper.getKeywords() != null && paper.getKeywords().size() > 0)
            keywords.addAll(paper.getKeywords());

        // Automatic keyword extraction
        try {
            JSONObject keywordsResponse = KeywordsApi.getKeywords(paper.getText());
            keywords.addAll(KeywordsApi.extractKeywords(keywordsResponse));
        } catch (Exception exception) {
            throw new KeywordServiceNotAvailableException("Keyword service could not be reached.");
        }

        // Store for relation purpose
        paper = paperRepository.save(paper);

        authors = authorService.saveMultiple(authors);
        paper.setAuthors(authors);

        keywords = keywordService.saveMultiple(keywords);
        paper.setKeywords(keywords);

        //TODO add paper to user
        paper = paperRepository.save(paper);

        return paper;
    }
}
