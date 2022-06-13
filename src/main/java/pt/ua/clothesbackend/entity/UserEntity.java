package pt.ua.clothesbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
public class UserEntity {

    public UserEntity() {}

    public UserEntity(String firstName, String lastName, String email, String password) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long userId;

    @Column(name = "name", nullable = false, length = 100)
    @Getter
    @Setter
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    @Getter
    @Setter
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    @Getter
    @Setter
    private String password;



}
