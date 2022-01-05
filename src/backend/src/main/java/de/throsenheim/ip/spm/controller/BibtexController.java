package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.BibtexResponse;
import de.throsenheim.ip.spm.util.ArxivApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

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
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BibtexResponse> bibtex(@PathVariable("id") String id) throws Exception {
        try {
            BibtexResponse bibtex = new BibtexResponse(ArxivApi.getBibtexById(id));
            return ResponseEntity.ok(bibtex);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new Exception("An error occured while trying to retrieve bibtex information");
        }
    }
}
