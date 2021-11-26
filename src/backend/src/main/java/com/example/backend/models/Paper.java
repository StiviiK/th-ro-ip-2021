package com.example.backend.models;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "paper")
public class Paper {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "bibtex")
    private String bibtex;

    @Column(name = "text")
    private String text;

    @Column(name = "keywords")
    @OneToMany
    private List<Keyword> keywords;

    public Paper(String url, String title, String author, String bibtex, String text, List<Keyword keywords>){
        this.url = url;
        this.title = title;
        this.author = author;
        this.bibtex = bibtex;
        this.text = text;
        this.keywords = keywords;
    }


}
