package de.throsenheim.ip.spm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/***
 * Contains all the information from a user
 * saved by the database.
 *
 * @author Alessandro Soro
 * @author Stefan Kürzeder
 */
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
    // NOTE: Type is required, even tho it is deprecated it does not work without it
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
    /**
     * Model of a user.
     * @param username Username of a user.
     * @param password Password of a user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of list of added papers.
     */
    public Set<Paper> getPapers() {
        return papers;
    }

    /**
     * Adds the liked paper to the list of a user.
     * @return False if paper was in list
     * @param likedPaper Liked paper.
     */
    public boolean addLikedPaper(Paper likedPaper) {
        return this.likedPapers.add(likedPaper);
    }

    /**
     * Adds the paper to the added papers list of a user.
     * @return False if paper was in list
     * @param paper Added paper.
     */
    public boolean addPaper(Paper paper) {
        return this.papers.add(paper);
    }

    /**
     * Liked Paper that needs to be removed from the list of liked papers from a user.
     * @param toRemove Liked Paper to remove.
     */
    public void removeLikedPaper(Paper toRemove) {
        this.likedPapers.remove(toRemove);
    }

    /**
     * Removes paper to the added list of papers of a user.
     * Removes paper from liked paper, if the liked paper list contains
     * the paper that needs to be removed.
     * @param toRemove Paper to remove.
     */
    public void removePaper(Paper toRemove) {
        this.likedPapers.remove(toRemove);
        this.papers.remove(toRemove);
    }
}