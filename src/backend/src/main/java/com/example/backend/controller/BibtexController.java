package com.example.backend.controller;

import com.example.backend.service.BibtexApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class BibtexController {

    private final BibtexApiService bibtexApiService;

    public BibtexController(BibtexApiService bibtexApiService) {
        this.bibtexApiService = bibtexApiService;
    }

    @RequestMapping(value="/bibtex/{id}", method = RequestMethod.GET, produces = {"text/plain"})
    public ResponseEntity<String> bibtex(@PathVariable("id") String id) throws Exception {
        try {
            String bibtex = this.bibtexApiService.getBibtexById(id);
            return ResponseEntity.ok(bibtex);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new Exception("An error occured while trying to retrieve bibtex information");
        }
    }
}
