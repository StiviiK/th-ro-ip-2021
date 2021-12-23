package com.example.backend.controller;

import com.example.backend.exceptions.ArxivNotAvailableException;
import com.example.backend.exceptions.KeywordServiceNotAvailableException;
import com.example.backend.models.Paper;
import com.example.backend.service.LocalUserDetailsService;
import com.example.backend.service.PaperService;
import com.example.backend.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @RequestMapping(value = "/papers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Paper>> getPapers() {
        return ResponseEntity.ok(paperService.getPapers());
    }

    @RequestMapping(value = "/papers/{paperId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Paper> getPaper(@RequestBody @PathVariable("paperId") String paperId) {
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
        } catch (InterruptedException e) {
            System.out.println("Arxiv.org could not be reached.");
            return ResponseEntity.internalServerError();
        } catch (IOException e) {
            System.out.println("Arxiv.org could not be reached.");
            return ResponseEntity.internalServerError();
        } catch (URISyntaxException e) {
            System.out.println("Arxiv.org could not be reached.");
            return ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(paper);
    }
}
