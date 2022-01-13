package de.throsenheim.ip.spm.util;

import de.throsenheim.ip.spm.models.ArxivInformationResponse;
import de.throsenheim.ip.spm.models.Author;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

/***
 * Different helper functions to retrieve informations
 * from arxiv.org
 *
 * @author Lukas Metzner
 */
public class ArxivApi {

    private ArxivApi() {

    }

    /***
     * Calls the arxiv.org api and tries to retrieve the bibtex of a paper.
     * @param id arxiv.org id of the paper.
     * @return Bibtex as string.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static String getBibtexById(String id) throws IOException, InterruptedException {
        String bibtexBaseURL = "https://arxiv.org/bibtex/";
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bibtexBaseURL + id))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /***
     * Get additional information from arxiv.org like the titel, authors and the summary.
     * @param id arxiv.org id of the paper.
     * @return Response from the arxiv.org endpoint.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static ArxivInformationResponse getArxivInformation(String id) throws URISyntaxException, IOException, InterruptedException, ParserConfigurationException, SAXException {
        String baseUrl = "https://export.arxiv.org/api/query?";
        String url = new StringBuilder()
                .append(baseUrl)
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

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // Note: access to external entities is required
        DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();

        Document document = parser.parse(new InputSource(new StringReader(rawXML)));
        Element entry = (Element) document.getElementsByTagName("entry").item(0);
        String title = parseFromEntryAsString(entry, "title");
        String summary = parseFromEntryAsString(entry, "summary");
        List<Author> authors = parseAuthors(entry);

        return new ArxivInformationResponse(title, summary, authors, response.statusCode());
    }

    /***
     * Get content of tag in the arxiv.org xml response.
     * @param entry Arxiv.org XML.
     * @param tagName Tag to retrieve content from.
     * @return
     */
    private static String parseFromEntryAsString(Element entry, String tagName) {
        NodeList nodes = entry.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent().strip();
        }

        return tagName + "_not_found";
    }

    /***
     * Parse authors from arxiv.org xml response.
     * @param entry Arxiv.org XML.
     * @return
     */
    private static List<Author> parseAuthors(Element entry) {
        var items = new ArrayList<Author>();
        NodeList nodes = entry.getElementsByTagName("author");
        for (int i = 0; i < nodes.getLength(); i++){
            items.add(new Author(nodes.item(i).getTextContent().strip()));
        }
        return items;
    }
}
