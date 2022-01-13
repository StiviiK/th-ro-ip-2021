package de.throsenheim.ip.spm.models;

/**
 * Wrapper for the add paper request.
 *
 * @author Lukas Metzner
 */
public class AddPaperRequest {

    private String id;

    private String url;

    private String bibtex;

    public AddPaperRequest(String id, String url, String bibtex) {
        this.id = id;
        this.url = url;
        this.bibtex = bibtex;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getBibtex() {
        return bibtex;
    }
}
