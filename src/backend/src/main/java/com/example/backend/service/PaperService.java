package com.example.backend.service;

import com.example.backend.exceptions.ArxivNotAvailableException;
import com.example.backend.exceptions.KeywordServiceNotAvailableException;
import com.example.backend.exceptions.PaperNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repository.PaperRepository;
import com.example.backend.util.ArxivApi;
import com.example.backend.util.KeywordsApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static com.example.backend.util.ArxivApi.getBibtexById;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private LocalUserDetailsService myUserDetailsService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private AuthorService authorService;

    public List<Paper> getPapers() {
        return paperRepository.findAll();
    }

    public void deletePaper(Paper paper) {paperRepository.delete(paper);}

    public Paper getPaper(String paperId) {
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        if (optionalPaper.isEmpty()) {
            throw new PaperNotFoundException(String.format("User with id: '%s' not found", paperId));
        }
        return optionalPaper.get();

    }

    public Paper addPaper(Paper paper, String username)
            throws KeywordServiceNotAvailableException, ArxivNotAvailableException, InterruptedException, IOException, URISyntaxException {
        UserPrincipal user = (UserPrincipal) myUserDetailsService.loadUserByUsername(username);
        if (paper == null) {
            throw new NullPointerException("Paper was null");
        }

        if (paper.getBibtex() == null || paper.getBibtex().isEmpty()) {
            paper.setBibtex(getBibtexById(paper.getId()));
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

        // Abstract
        paper.setAbstract_(arxivInformation.getSummary());

        // Extract Keywords
        // If keywords entered by user
        List<Keyword> keywords = new ArrayList<>();
        if (paper.getKeywords() != null && paper.getKeywords().size() > 0)
            keywords.addAll(paper.getKeywords());

        // Automatic keyword extraction
        try {
            JSONObject keywordsResponse = KeywordsApi.getKeywords(paper.getAbstract_());
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

        paper = paperRepository.save(paper);
        myUserDetailsService.addPaper(user.getUser(), paper);

        return paper;
    }
}
