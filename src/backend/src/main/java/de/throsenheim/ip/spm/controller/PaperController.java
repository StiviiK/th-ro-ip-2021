package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.exceptions.ArxivNotAvailableException;
import de.throsenheim.ip.spm.exceptions.KeywordServiceNotAvailableException;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.service.PaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Paper>> getPapers() {
        return ResponseEntity.ok(paperService.getPapers());
    }

    /**
     * Deletes a paper from the database.
     * @param paperId Id of the paper to delete.
     * @return Deleted paper.
     */
    @RequestMapping(value = "/{paperId}", method = RequestMethod.DELETE, produces = {
            "application/json;charset=UTF-8" })
    public ResponseEntity<String> deletePaper(@RequestBody @PathVariable("paperId") String paperId) {
        var paperToDelete = paperService.getPaper(paperId);
        paperService.deletePaper(paperToDelete);
        return ResponseEntity.ok("Paper with Id" + paperId + "has been deleted");
    }

    /**
     * Adds a new paper to the database and retrieves the keywords and arxiv.org information
     * about the title, authors and the summary.
     * @param authentication User authentication to append the paper to an users paper list.
     * @param paper The paper to preprocess and add to the database.
     * @return The processed and stored paper.
     * @throws ArxivNotAvailableException
     * @throws KeywordServiceNotAvailableException
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
    public Object addPaper(Authentication authentication, @RequestBody Paper paper)
            throws ArxivNotAvailableException, KeywordServiceNotAvailableException {
        var principal = (UserDetails) authentication.getPrincipal();
        var username = principal.getUsername();
        try {
            paper = paperService.addPaper(paper, username);
        } catch (ArxivNotAvailableException | KeywordServiceNotAvailableException exception) {
            System.out.println("Keyword service could not be reached.");
            return ResponseEntity.internalServerError();
        } catch (InterruptedException | IOException | URISyntaxException e) {
            System.out.println("Arxiv.org could not be reached.");
            return ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(paper);
    }

    /**
     * Retrieve a specific paper from the database.
     * @param paperId Id of the paper in the database.
     * @return Paper from the database.
     */
    @RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
    public ResponseEntity<Paper> getPaper(@RequestBody @PathVariable("paperId") String paperId) {
        return ResponseEntity.ok(paperService.getPaper(paperId));
    }
}
