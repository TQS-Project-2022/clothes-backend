package pt.ua.clothesbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user")
public class UserEntity {

    public UserEntity() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserEntity)) {
            return false;
        }

        UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.getUserId(), other.getUserId())) {
            return false;
        }
        if (!Objects.equals(this.getFirstName(), other.getFirstName())) {
            return false;
        }
        if (!Objects.equals(this.getLastName(), other.getLastName())) {
            return false;
        }
        if (!Objects.equals(this.getEmail(), other.getEmail())) {
            return false;
        }
        if (!Objects.equals(this.getPassword(), other.getPassword())) {
            return false;
        }
        return true;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @Getter
    @Setter
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    @Getter
    @Setter
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    @Getter
    @Setter
    private String password;



}
