package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BibtexResponse {
    private String bibtex;

    public BibtexResponse(String bibtex) {
        this.bibtex = bibtex;
    }
}
