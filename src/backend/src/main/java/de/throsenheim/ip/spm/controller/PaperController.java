package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.exceptions.ArxivNotAvailableException;
import de.throsenheim.ip.spm.exceptions.KeywordServiceNotAvailableException;
import de.throsenheim.ip.spm.models.AddPaperRequest;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.service.PaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Controller that takes care of adding, retrieving and deleting papers.
 *
 * @author Lukas Metzner
 * @author Alessandro Soro
 */
@RestController
@RequestMapping(value = "/papers", produces = { "application/json;charset=UTF-8" })
public class PaperController {
    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping()
    public ResponseEntity<List<Paper>> getPapers() {
        return ResponseEntity.ok(paperService.getPapers());
    }

    /**
     * Deletes a paper from the database.
     * @param paperId Id of the paper to delete.
     * @return Deleted paper.
     */
    @DeleteMapping(value = "/{paperId}")
    public ResponseEntity<String> deletePaper(@RequestBody @PathVariable("paperId") String paperId) {
        var paperToDelete = paperService.getPaper(paperId);
        paperService.deletePaper(paperToDelete);
        return ResponseEntity.ok("Paper with Id" + paperId + "has been deleted");
    }

    /**
     * Adds a new paper to the database and retrieves the keywords and arxiv.org information
     * about the title, authors and the summary.
     * @param authentication User authentication to append the paper to an users paper list.
     * @param addPaperRequest The paper to preprocess and add to the database, wrapped in a AddPaperRequest.
     * @return The processed and stored paper.
     * @throws ArxivNotAvailableException
     * @throws KeywordServiceNotAvailableException
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @PutMapping(value = "")
    public Object addPaper(Authentication authentication, @RequestBody AddPaperRequest addPaperRequest)
            throws ArxivNotAvailableException, KeywordServiceNotAvailableException, IOException, URISyntaxException, InterruptedException, ParserConfigurationException, SAXException {
        var principal = (UserDetails) authentication.getPrincipal();
        var username = principal.getUsername();
        var paper = new Paper(addPaperRequest);
        paper = paperService.addPaper(paper, username);
        return ResponseEntity.ok(paper);
    }

    /**
     * Retrieve a specific paper from the database.
     * @param paperId Id of the paper in the database.
     * @return Paper from the database.
     */
    @GetMapping(value = "/{paperId}")
    public ResponseEntity<Paper> getPaper(@RequestBody @PathVariable("paperId") String paperId) {
        return ResponseEntity.ok(paperService.getPaper(paperId));
    }
}
