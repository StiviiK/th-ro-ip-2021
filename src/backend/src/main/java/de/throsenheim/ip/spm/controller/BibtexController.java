package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.BibtexResponse;
import de.throsenheim.ip.spm.util.ArxivApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller that acts as an interface to arxiv.org for the Bibtex api.
 *
 * @author Lukas Metzner
 */
@RestController
@RequestMapping(value = "/bibtex", produces = {"application/json;charset=UTF-8"})
public class BibtexController {

    /**
     * Retrieve the Bibtex string for a single paper.
     * @param id Arxiv.org id for the paper.
     * @return Response that contains the Bibtex string.
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BibtexResponse> bibtex(@PathVariable("id") String id) throws IOException, InterruptedException {
        BibtexResponse bibtex = new BibtexResponse(ArxivApi.getBibtexById(id));
        return ResponseEntity.ok(bibtex);
    }
}
