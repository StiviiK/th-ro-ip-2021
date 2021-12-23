package com.example.backend.models;
import lombok.*;

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
    @GeneratedValue
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
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
}
