package com.example.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "keywords")
public class Keyword {

    @Id
    @Column(name = "keyword_id")
    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }
}
