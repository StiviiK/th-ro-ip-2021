package de.throsenheim.ip.spm.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue()
    @Column(name = "user_id", updatable = false, nullable = false)
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore()
    private String password;

    @Column(name = "papers_user")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "papers_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "paper_id"))
    Set<Paper> papers;

    @Column(name = "liked_papers")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "liked_papers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "paper_id"))
    Set<Paper> likedPapers;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Set<Paper> getPapers() {
        return papers;
    }

    public void addLikedPaper(Paper likedPaper) {
        this.likedPapers.add(likedPaper);
    }

    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    public void removeLikedPaper(Paper toRemove) {
        this.likedPapers.remove(toRemove);
    }

    public void removePaper(Paper toRemove) {
        if(this.likedPapers.contains(toRemove)){
            this.likedPapers.remove(toRemove);
        }
        this.papers.remove(toRemove);
    }
}