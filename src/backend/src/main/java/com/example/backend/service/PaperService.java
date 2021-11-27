package com.example.backend.service;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
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

    public Paper addPaper(Paper paper, String username) throws NullPointerException, InterruptedException, IOException, URISyntaxException, ParserConfigurationException, SAXException {
        var user = myUserDetailsService.loadUserByUsername(username);
        if (paper == null) {
            throw new NullPointerException("Paper was null");
        }
        // Store for relation purpose
        paper = paperRepository.save(paper);

        // Retrieve paper information
        ArxivInformationResponse arxivInformation = ArxivApi.getArxivInformation(paper.getId());

        // Authors
        List<Author> authors = arxivInformation.getAuthors();
        authors = authorService.saveMultiple(authors);
        paper.setAuthors(authors);

        // Title
        paper.setTitle(arxivInformation.getTitle());

        // Text
        // TODO change text to actual text not just summary
        paper.setText(arxivInformation.getSummary());

        // Extract Keywords
        JSONObject keywordsResponse = KeywordsApi.getKeywords(paper.getText());
        List<Keyword> keywords = KeywordsApi.extractKeywords(keywordsResponse);
        keywords = keywordService.saveMultiple(keywords);
        paper.setKeywords(keywords);

        //TODO add paper to user
        paper = paperRepository.save(paper);

        return paper;
    }
}
