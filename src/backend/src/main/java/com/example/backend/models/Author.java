package com.example.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * Instance of a single author, which is identified
 * by his name itself.
 *
 * @author Lukas Metzner
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "author_id")
    private String name;

    public Author (String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
