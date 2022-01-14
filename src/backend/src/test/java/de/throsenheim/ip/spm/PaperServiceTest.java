package de.throsenheim.ip.spm;

import de.throsenheim.ip.spm.models.AddPaperRequest;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.repository.PaperRepository;
import de.throsenheim.ip.spm.service.PaperService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Paper Service
 * @author Alessandro Soro
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PaperServiceTest {

    @Autowired
    private PaperService paperService;

    @MockBean
    private PaperRepository paperRepository;

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
        String url = "https://arxiv.org/pdf/2201.03382.pdf";
        AddPaperRequest paperToAdd = new AddPaperRequest(id, url, bibtex);
        Paper expectedPaper = new Paper(paperToAdd);
        String testUser = "foo";

        Mockito.when(paperRepository.save(expectedPaper))
                .thenReturn(expectedPaper);

        Paper found = paperService.addPaper(expectedPaper, testUser);

        assertEquals(found.getId(), expectedPaper.getId());
    }
}
