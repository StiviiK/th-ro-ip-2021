package com.example.backend.util;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KeywordsApi {
    private static String BaseURL = "http://localhost:5000/extract?text=";

    public static JSONObject getKeywords(String text) throws URISyntaxException, IOException, InterruptedException {
        String url = new StringBuilder()
                .append(BaseURL)
                .append(URLEncoder.encode(text, "UTF-8"))
                .toString();
        HttpRequest request = HttpRequest
                .newBuilder(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}
