package com.example.backend;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.backend.util.BibtexApi.getBibtexById;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BibtexApiTest {

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
        var bibtex = getBibtexById(arxivId);
        assertEquals(expected_bibtex, bibtex);
    }
}
