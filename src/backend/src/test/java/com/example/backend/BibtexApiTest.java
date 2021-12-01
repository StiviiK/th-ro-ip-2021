package com.example.backend;

import com.example.backend.service.BibtexApiService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BibtexApiTest {

    private final BibtexApiService bibtexApiService;

    public BibtexApiTest() {
        this.bibtexApiService = new BibtexApiService();
    }

    @Test
    public void testGetBibtex() throws InterruptedException, IOException, URISyntaxException {
        var arxivId = "1906.01502";
        var expected_bibtex = "@misc{pires2019multilingual,\n" +
                "      title={How multilingual is Multilingual BERT?}, \n" +
                "      author={Telmo Pires and Eva Schlinger and Dan Garrette},\n" +
                "      year={2019},\n" +
                "      eprint={1906.01502},\n" +
                "      archivePrefix={arXiv},\n" +
                "      primaryClass={cs.CL}\n" +
                "}";
        var bibtex = this.bibtexApiService.getBibtexById(arxivId);
        assertEquals(expected_bibtex, bibtex);
    }
}
