package de.throsenheim.ip.spm;

import de.throsenheim.ip.spm.models.ArxivInformationResponse;
import de.throsenheim.ip.spm.models.Author;
import de.throsenheim.ip.spm.util.ArxivApi;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArxivApiTest {

    private static String arxivId = "1906.01502";

    @Test
    public void testGetBibtex() throws InterruptedException, IOException, URISyntaxException {
        var expected_bibtex = "@misc{pires2019multilingual,\n" +
                "      title={How multilingual is Multilingual BERT?}, \n" +
                "      author={Telmo Pires and Eva Schlinger and Dan Garrette},\n" +
                "      year={2019},\n" +
                "      eprint={1906.01502},\n" +
                "      archivePrefix={arXiv},\n" +
                "      primaryClass={cs.CL}\n" +
                "}";
        var bibtex = ArxivApi.getBibtexById(arxivId);
        assertEquals(expected_bibtex, bibtex);
    }

    @Test
    public void testGetArxivInformation() throws InterruptedException, IOException, ParserConfigurationException, SAXException, URISyntaxException {
        String title = "How multilingual is Multilingual BERT?";
        String summary = "In this paper, we show that Multilingual BERT (M-BERT), released by Devlin et\n" +
                "al. (2018) as a single language model pre-trained from monolingual corpora in\n" +
                "104 languages, is surprisingly good at zero-shot cross-lingual model transfer,\n" +
                "in which task-specific annotations in one language are used to fine-tune the\n" +
                "model for evaluation in another language. To understand why, we present a large\n" +
                "number of probing experiments, showing that transfer is possible even to\n" +
                "languages in different scripts, that transfer works best between typologically\n" +
                "similar languages, that monolingual corpora can train models for\n" +
                "code-switching, and that the model can find translation pairs. From these\n" +
                "results, we can conclude that M-BERT does create multilingual representations,\n" +
                "but that these representations exhibit systematic deficiencies affecting\n" +
                "certain language pairs.";
        List<Author> authors = Arrays.asList(
                new Author("Telmo Pires"),
                new Author("Eva Schlinger"),
                new Author("Dan Garrette")
        );
        int statusCode = 200;

        ArxivInformationResponse expected = new ArxivInformationResponse(title, summary, authors, statusCode);
        ArxivInformationResponse response = ArxivApi.getArxivInformation(arxivId);
        assertEquals(expected.getTitle(), response.getTitle());
        assertEquals(expected.getAbstract_(), response.getAbstract_());
        assertEquals(expected.getAuthors(), response.getAuthors());
        assertEquals(expected.getStatusCode(), response.getStatusCode());
    }
}
