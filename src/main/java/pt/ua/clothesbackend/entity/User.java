package pt.ua.clothesbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    public User() {
        super();
    }

    @Column(name = "name", nullable = false, length = 100)
    @Getter
    @Setter
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    @Getter
    @Setter
    private String email;

    // TODO Necessary to encrypt password
    @Column(name = "password", nullable = false, length = 64)
    @Getter
    @Setter
    private String password;



}
