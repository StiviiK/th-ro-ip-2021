package de.throsenheim.ip.spm;

import de.throsenheim.ip.spm.util.KeywordsApi;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Keyword service
 * @author Lukas Metzner
 */
class KeywordsApiTest {

    @Test
    void testKeywordsApi() throws InterruptedException, IOException, URISyntaxException {
        JSONObject extracted = KeywordsApi.getKeywords("Google just released a new software project.");
        var keywords = extracted.getJSONArray("keywords");
        var keyword = keywords.getJSONArray(2);
        assertEquals("Google", keyword.getString(0));
    }
}
