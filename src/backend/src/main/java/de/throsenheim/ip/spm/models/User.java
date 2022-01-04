package de.throsenheim.ip.spm.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "liked_papers")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "liked_papers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "paper_id"))
    List<Paper> likedPapers;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addLikedPaper(Paper likedPaper) {
        this.likedPapers.add(likedPaper);
    }

    public void removeLikedPaper(Paper toRemove) {
        this.likedPapers.remove(toRemove);
    }
}