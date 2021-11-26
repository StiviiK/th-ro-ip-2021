package com.example.backend;

import com.example.backend.util.KeywordsApi;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeywordsApiTest {

    @Test
    public void testKeywordsApi() throws InterruptedException, IOException, URISyntaxException {
        JSONObject extracted = KeywordsApi.getKeywords("Google just released a new software project.");
        var keywords = extracted.getJSONArray("keywords");
        var keyword = keywords.getJSONArray(2);
        assertEquals("Google", keyword.getString(0));
    }
}
