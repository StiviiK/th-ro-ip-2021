package de.throsenheim.ip.spm;

import de.throsenheim.ip.spm.models.AddPaperRequest;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.service.PaperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test Paper Service
 * @author Alessandro Soro
 */
@ExtendWith(MockitoExtension.class)
public class PaperServiceTest {
    @MockBean
    private PaperService paperService;

    @Test
    void testAddPaper() throws IOException, URISyntaxException, ParserConfigurationException, InterruptedException, SAXException {
        String id = "2201.03382";
        String bibtex = "@misc{souza2022bert,\n" +
                "      title={BERT for Sentiment Analysis: Pre-trained and Fine-Tuned Alternatives}, \n" +
                "      author={Frederico Souza and JoÃ£o Filho},\n" +
                "      year={2022},\n" +
                "      eprint={2201.03382},\n" +
                "      archivePrefix={arXiv},\n" +
                "      primaryClass={cs.CL}\n" +
                "}\n";
        String url = "https://arxiv.org/abs/2201.03382";
        AddPaperRequest paperToAdd = new AddPaperRequest(id, url, bibtex);
        Paper expectedPaper = new Paper(paperToAdd);
        String username = "foo";
        Paper result = paperService.addPaper(expectedPaper, username);
        assertEquals(expectedPaper, result);

        paperService.deletePaper(result);
        assertFalse(paperService.getPapers().contains(result));
    }

}
