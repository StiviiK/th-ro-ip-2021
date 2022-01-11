package de.throsenheim.ip.spm.util;

import de.throsenheim.ip.spm.models.Keyword;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/***
 * Helper functions to call the KeywordService and extract
 * all keywords for a given text.
 *
 * @author Lukas Metzner
 */
public class KeywordsApi {
    private static final String BaseURL = System.getenv("KEYWORD_SERVICE_URL");

    /***
     * Call the KeywordService api and retrieve the keywords from text.
     * @param text Text to extract the keywords from.
     * @return JSON response from the KeywordsService.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static JSONObject getKeywords(String text) throws URISyntaxException, IOException, InterruptedException {
        String url = String.format("%s/extract?text=%s", BaseURL, URLEncoder.encode(text, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest
                .newBuilder(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

    /***
     * Extract keywords from the json response.
     * @param response Response from the KeywordService.
     * @return List of Keywords.
     */
    public static List<Keyword> extractKeywords(JSONObject response) {
        var keywords = new ArrayList<Keyword>();
        var keywordsResponse = response.getJSONArray("keywords");

        for (int i = 0; i < keywordsResponse.length(); i++) {
            var keywordObject = keywordsResponse.getJSONArray(i);

            // Keywords are Python tuples formatted as Arrays: [Keyword, ModelOutput]
            var keyword = keywordObject.getString(0);
            keywords.add(new Keyword(keyword));
        }
        return keywords;
    }
}
