package com.example.backend.controller;

import com.example.backend.models.BibtexResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.backend.util.ArxivApi.getBibtexById;

/**
 * Allows the frontend to retrieve Bibtex information for papers.
 *
 * @author Lukas Metzner
 */
@RestController
public class BibtexController {

    /**
     * Retrieve the Bibtex of an arxiv.org paper by its id.
     * This way the Bibtex can be shown in the frontend and edited by the user.
     * @param id Arxiv.org id of the paper
     * @return Bibtex wrapped in a BibtexResponse.
     * @throws Exception
     */
    @RequestMapping(value = "/bibtex/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<BibtexResponse> bibtex(@PathVariable("id") String id) throws Exception {
        try {
            BibtexResponse bibtex = new BibtexResponse(getBibtexById(id));
            return ResponseEntity.ok(bibtex);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new Exception("An error occured while trying to retrieve bibtex information");
        }
    }
}
