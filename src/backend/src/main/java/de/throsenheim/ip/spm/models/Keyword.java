package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Instance of a single keyword, which is identified
 * by the keyword itself.
 *
 * @author Lukas Metzner
 */
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
