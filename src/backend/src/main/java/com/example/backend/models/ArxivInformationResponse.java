package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
