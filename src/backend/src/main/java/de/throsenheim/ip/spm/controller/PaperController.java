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

@RestController
@RequestMapping(value = "/papers", produces = {"application/json;charset=UTF-8"})
public class PaperController {
    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Paper>> getPapers() {
        return ResponseEntity.ok(paperService.getPapers());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object addPaper(Authentication authentication, @RequestBody Paper paper) throws ArxivNotAvailableException, KeywordServiceNotAvailableException {
        var principal = (UserDetails)authentication.getPrincipal();
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

    @RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
    public ResponseEntity<Paper> getPaper(@RequestBody @PathVariable("paperId") String paperId) {
        return ResponseEntity.ok(paperService.getPaper(paperId));
    }
}
