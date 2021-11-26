package com.example.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @Column(name = "keyword")
    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }
}
