package com.example.backend.models;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "bibtex")
    private String bibtex;

    // https://arxiv.org/help/prep -> "abstracts longer than 1920 characters will not be accepted;"
    @Column(name = "abstract_", length = 1920)
    private String abstract_;

    @Column(name = "keywords")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "papers_kewyords",
        joinColumns = {
            @JoinColumn(name = "paper_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "keyword_id")
        }
    )
    private List<Keyword> keywords;

    public Paper(String url, String title, List<Author> authors, String bibtex, String abstract_, List<Keyword> keywords){
        this.url = url;
        this.title = title;
        this.authors = authors;
        this.bibtex = bibtex;
        this.abstract_ = abstract_;
        this.keywords = keywords;
    }


}
