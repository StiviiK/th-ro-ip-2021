package com.example.backend.controller;

import com.example.backend.models.Paper;
import com.example.backend.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@EnableAsync
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
    public ResponseEntity<Paper> addPaper(Authentication authentication, @RequestBody Paper paper) {
        var principal = (UserDetails)authentication.getPrincipal();
        System.out.println(principal.getUsername());
        return ResponseEntity.ok(paper);
    }
}
