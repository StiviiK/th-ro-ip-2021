package com.example.backend.util;

import com.example.backend.models.ArxivInformationResponse;
import com.example.backend.models.Author;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ArxivApi {
    private static String bibtexBaseURL = "https://arxiv.org/bibtex/";
    private static String BaseUrl = "https://export.arxiv.org/api/query?";
    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public static String getBibtexById(String id) throws URISyntaxException, IOException, InterruptedException {
        String url = new StringBuilder()
                .append(bibtexBaseURL)
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

    public static ArxivInformationResponse getArxivInformation(String id) throws URISyntaxException, IOException, InterruptedException, ParserConfigurationException, SAXException {
        String url = new StringBuilder()
                .append(BaseUrl)
                .append("id_list=")
                .append(id)
                .append("&start=0")
                .append("&max_results=1")
                .toString();
        HttpRequest request = HttpRequest
                .newBuilder(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String rawXML = response.body();
        DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();
        Document document = parser.parse(new InputSource(new StringReader(rawXML)));
        Element entry = (Element) document.getElementsByTagName("entry").item(0);
        String title = parseFromEntryAsString(entry, "title");
        String summary = parseFromEntryAsString(entry, "summary");
        List<Author> authors = parseAuthors(entry);

        return new ArxivInformationResponse(title, summary, authors, response.statusCode());
    }

    private static String parseFromEntryAsString(Element entry, String tagName) {
        NodeList nodes = entry.getElementsByTagName(tagName);
        for (int i = 0; i < nodes.getLength(); i++){
            return nodes.item(i).getTextContent().strip();
        }
        return tagName + "_not_found";
    }

    private static List<Author> parseAuthors(Element entry) {
        var items = new ArrayList<Author>();
        NodeList nodes = entry.getElementsByTagName("author");
        for (int i = 0; i < nodes.getLength(); i++){
            items.add(new Author(nodes.item(i).getTextContent().strip()));
        }
        return items;
    }
}
