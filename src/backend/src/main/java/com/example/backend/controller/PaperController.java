package com.example.backend.controller;

import com.example.backend.exceptions.ArxivNotAvailableException;
import com.example.backend.exceptions.KeywordServiceNotAvailableException;
import com.example.backend.models.Paper;
import com.example.backend.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PaperController {
    @Autowired
    private PaperService paperService;

    @RequestMapping(value = "/papers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Paper>> getPapers() {
        return ResponseEntity.ok(paperService.getPapers());
    }

    @RequestMapping(value = "/paper/{paperId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Paper> getPaper(@RequestBody @PathVariable("paperId") UUID paperId) {
        return ResponseEntity.ok(paperService.getPaper(paperId));
    }

    @RequestMapping(value = "/papers", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Object addPaper(Authentication authentication, @RequestBody Paper paper) throws ArxivNotAvailableException, KeywordServiceNotAvailableException {
        var principal = (UserDetails)authentication.getPrincipal();
        var username = principal.getUsername();
        try {
            paper = paperService.addPaper(paper, username);
        } catch (ArxivNotAvailableException | KeywordServiceNotAvailableException exception) {
            System.out.println("Keyword service could not be reached.");
            return ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(paper);
    }
}
