package de.throsenheim.ip.spm.models;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBibtex() {
        return bibtex;
    }

    public void setBibtex(String bibtex) {
        this.bibtex = bibtex;
    }
}
