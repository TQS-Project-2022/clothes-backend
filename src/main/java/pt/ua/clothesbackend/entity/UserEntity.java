package pt.ua.clothesbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user")
public class UserEntity {

    public UserEntity() {}

    public UserEntity(
            String firstName, String lastName,
            String email, String password)
    {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id", nullable = false, unique = true)
    @Getter
    @Setter
    private long userId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        if (getUserId() != that.getUserId()) {
            return false;
        }
        if (!Objects.equals(getFirstName(), that.getFirstName())) {
            return false;
        }
        if (!Objects.equals(getLastName(), that.getLastName())) {
            return false;
        }
        if (!Objects.equals(getEmail(), that.getEmail())) {
            return false;
        }
        return Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getFirstName(), getLastName(), getEmail(), getPassword());
    }
}
