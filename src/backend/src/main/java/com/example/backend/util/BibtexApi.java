package com.example.backend.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BibtexApi {
    private static String BaseURL = "https://arxiv.org/bibtex/";

    public static String getBibtexById(String id) throws URISyntaxException, IOException, InterruptedException {
        String url = new StringBuilder()
                .append(BaseURL)
                .append(id)
                .toString();
        HttpRequest request = HttpRequest
                .newBuilder(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
