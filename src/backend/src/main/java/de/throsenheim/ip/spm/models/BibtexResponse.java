package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BibtexResponse {
    private String bibtex;

    /**
     * Wrapper for bibtex entry that is retrieved from the arxiv.org api.
     * @param bibtex Bibtex as string. Contains newlines.
     */
    public BibtexResponse(String bibtex) {
        this.bibtex = bibtex;
    }
}
