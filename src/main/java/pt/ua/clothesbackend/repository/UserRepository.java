package pt.ua.clothesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.ua.clothesbackend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

}
