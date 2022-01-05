package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response from arxiv.org, which contains the title, summary
 * and authors. It also includes the status code of the response.
 *
 * @author Lukas Metzner
 */
@Getter
@Setter
public class ArxivInformationResponse {
    private String title;
    private String summary;
    private List<Author> authors;
    private int statusCode;

    public ArxivInformationResponse(String title, String summary, List<Author> authors, int statusCode) {
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ArxivInformationResponse{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", authors=" + authors +
                ", statusCode=" + statusCode +
                '}';
    }
}
