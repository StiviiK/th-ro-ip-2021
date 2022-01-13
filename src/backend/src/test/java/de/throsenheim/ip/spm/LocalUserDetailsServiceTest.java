package de.throsenheim.ip.spm;

import de.throsenheim.ip.spm.models.AddPaperRequest;
import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Local User Details Service
 * @author Alessandro Soro
 */
@ExtendWith(MockitoExtension.class)
public class LocalUserDetailsServiceTest {

    @MockBean
    private LocalUserDetailsService userDetailsService;

    @Test
    void testLocalUserDetailsService() {
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
        Paper testPaper = new Paper(paperToAdd);
        User testUser = new User("test", "test");

        userDetailsService.addPaper(testUser, testPaper);
        assertTrue(testUser.getPapers().contains(testPaper));

        userDetailsService.addLikedPaper(testUser, testPaper);
        assertTrue(testUser.getPapers().contains(testPaper));

        userDetailsService.removeLikedPaper(testUser, testPaper);
        assertFalse(testUser.getLikedPapers().contains(testPaper));

        userDetailsService.removeAddedPaper(testUser, testPaper);
        assertFalse(testUser.getPapers().contains(testPaper));
    }
}
