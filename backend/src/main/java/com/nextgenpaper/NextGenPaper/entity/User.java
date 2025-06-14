package com.nextgenpaper.NextGenPaper.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userId;

    @Column(unique = true)
    private String username;

    private String fullName;

    private String instituteName;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;
    public User(String userName, String password){
        this.username = userName;
        this.password = password;
    }

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<QuestionPaper> questionPaper;
}
