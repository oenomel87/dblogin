package dev.oenomel.guide.dblogin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mysUserId;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String name;

    private String role;
}
