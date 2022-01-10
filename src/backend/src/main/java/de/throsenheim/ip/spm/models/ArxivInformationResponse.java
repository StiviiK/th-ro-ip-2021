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
    private String abstract_;
    private List<Author> authors;
    private int statusCode;

    /**
     * Wrapper for the arxiv.org api response to retrieve additional information about a paper.
     * @param title The title of the paper.
     * @param abstract_ The abstract of the paper.
     * @param authors The authors of the paper.
     * @param statusCode The status code of the arxiv.org api response.
     */
    public ArxivInformationResponse(String title, String abstract_, List<Author> authors, int statusCode) {
        this.title = title;
        this.abstract_ = abstract_;
        this.authors = authors;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ArxivInformationResponse{" +
                "title='" + title + '\'' +
                ", summary='" + abstract_ + '\'' +
                ", authors=" + authors +
                ", statusCode=" + statusCode +
                '}';
    }
}
