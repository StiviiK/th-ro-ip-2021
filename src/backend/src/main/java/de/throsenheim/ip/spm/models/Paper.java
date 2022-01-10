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
    private String abstract_;

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
     * Model of a paper from arxiv.org.
     * @param url URL to the arxiv.org paper. Entered by the user.
     * @param title Title of the paper. Retrieved using the arxiv.org api.
     * @param authors Authors of the paper. Retrieved using the arxiv.org api.
     * @param bibtex Bibtex entry for the paper. Retrieved using the arxiv.org api.
     * @param abstract_ Abstract of the paper. Retrieved using the arxiv.org api.
     * @param keywords Extract keywords from the abstract using NLP methods.
     */
    public Paper(String url, String title, List<Author> authors, String bibtex, String abstract_, List<Keyword> keywords){
        this.url = url;
        this.title = title;
        this.authors = authors;
        this.bibtex = bibtex;
        this.abstract_ = abstract_;
        this.keywords = keywords;
    }
}
