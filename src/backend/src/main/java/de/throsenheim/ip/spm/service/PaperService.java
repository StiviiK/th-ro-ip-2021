package de.throsenheim.ip.spm.service;

import de.throsenheim.ip.spm.exceptions.ArxivNotAvailableException;
import de.throsenheim.ip.spm.exceptions.KeywordServiceNotAvailableException;
import de.throsenheim.ip.spm.exceptions.PaperNotFoundException;
import de.throsenheim.ip.spm.models.ArxivInformationResponse;
import de.throsenheim.ip.spm.models.Author;
import de.throsenheim.ip.spm.models.Keyword;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.models.UserPrincipal;
import de.throsenheim.ip.spm.repository.PaperRepository;
import de.throsenheim.ip.spm.util.ArxivApi;
import de.throsenheim.ip.spm.util.KeywordsApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Takes care of the CRUD operations for a paper.
 *
 * @author Lukas Metzner
 * @author Alessandro Soro
 */
@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final LocalUserDetailsService myUserDetailsService;
    private final KeywordService keywordService;
    private final AuthorService authorService;

    public PaperService(PaperRepository paperRepository, LocalUserDetailsService myUserDetailsService, KeywordService keywordService, AuthorService authorService) {
        this.paperRepository = paperRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.keywordService = keywordService;
        this.authorService = authorService;
    }

    /**
     * Return all papers.
     * @return List of papers.
     */
    public List<Paper> getPapers() {
        return paperRepository.findAll();
    }

    /**
     * Deletes paper.
     * @param paper Paper to delete.
     */
    public void deletePaper(Paper paper) {paperRepository.delete(paper);}

    /**
     * Returns paper with the paperId, if found.
     * @param paperId PaperId of paper.
     * @return List of papers.
     */
    public Paper getPaper(String paperId) {
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        if (optionalPaper.isEmpty()) {
            throw new PaperNotFoundException(String.format("Paper with id: '%s' not found", paperId));
        }
        return optionalPaper.get();

    }

    /***
     * Add a new paper to the database or update one. First retrieve the bibtex
     * if not provided by through the frontend. After this get additional information from arxiv.org
     * (title, authors, summary). Finally extract the keywords using the KeywordsService and store the paper.
     * @param paper Paper to process and store.
     * @param username Username that is associated with the paper.
     * @return Paper that got created.
     * @throws KeywordServiceNotAvailableException
     * @throws ArxivNotAvailableException
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Paper addPaper(Paper paper, String username)
            throws KeywordServiceNotAvailableException, ArxivNotAvailableException, InterruptedException, IOException, URISyntaxException, ParserConfigurationException, SAXException {
        UserPrincipal user = (UserPrincipal) myUserDetailsService.loadUserByUsername(username);
        if (paper == null) {
            throw new NullPointerException("Paper was null");
        }

        if (paper.getBibtex() == null || paper.getBibtex().isEmpty()) {
            paper.setBibtex(ArxivApi.getBibtexById(paper.getId()));
        }

        // Retrieve paper information
        ArxivInformationResponse arxivInformation = ArxivApi.getArxivInformation(paper.getId());

        // Authors
        List<Author> authors = arxivInformation.getAuthors();

        // Title
        paper.setTitle(arxivInformation.getTitle());

        // Abstract
        paper.setAbstractString(arxivInformation.getAbstractString());

        // Extract Keywords
        // If keywords entered by user
        List<Keyword> keywords = new ArrayList<>();
        if (paper.getKeywords() != null && !paper.getKeywords().isEmpty())
            keywords.addAll(paper.getKeywords());

        // Automatic keyword extraction
        JSONObject keywordsResponse = KeywordsApi.getKeywords(paper.getAbstractString());
        keywords.addAll(KeywordsApi.extractKeywords(keywordsResponse));

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
