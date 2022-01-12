package de.throsenheim.ip.spm.models;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/***
 * Contains all relevant information for a paper
 * to be stored in the database.
 *
 * @author Alessandro Soro
 * @author Lukas Metzner
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "papers")
public class Paper {
    @Id
    @Column(name = "paper_id")
    private String id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "papers_authors",
            joinColumns = {
                    @JoinColumn(name = "paper_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "author_id")
            }
    )
    private List<Author> authors;

    @Column(name = "bibtex", length = 10000)
    private String bibtex;

    // https://arxiv.org/help/prep -> "abstracts longer than 1920 characters will not be accepted;"
    @Column(name = "abstract_", length = 1920)
    private String abstractString;

    @Column(name = "keywords")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "papers_keywords",
        joinColumns = {
            @JoinColumn(name = "paper_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "keyword_id")
        }
    )

    private List<Keyword> keywords;

    /**
     * Mapping from add paper request to paper object.
     * @param addPaperRequest Request object with the information.
     */
    public Paper(AddPaperRequest addPaperRequest) {
        this.id = addPaperRequest.getId();
        this.url = addPaperRequest.getUrl();
        this.bibtex = addPaperRequest.getBibtex();
    }

    /**
     * Model of a paper from arxiv.org.
     * @param url URL to the arxiv.org paper. Entered by the user.
     * @param title Title of the paper. Retrieved using the arxiv.org api.
     * @param authors Authors of the paper. Retrieved using the arxiv.org api.
     * @param bibtex Bibtex entry for the paper. Retrieved using the arxiv.org api.
     * @param abstractString Abstract of the paper. Retrieved using the arxiv.org api.
     * @param keywords Extract keywords from the abstract using NLP methods.
     */
    public Paper(String id, String url, String title, List<Author> authors, String bibtex, String abstractString, List<Keyword> keywords){
        this.id = id;
        this.url = url;
        this.title = title;
        this.authors = authors;
        this.bibtex = bibtex;
        this.abstractString = abstractString;
        this.keywords = keywords;
    }
}
