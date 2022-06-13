package pt.ua.clothesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.ua.clothesbackend.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("Select u FROM UserEntity u WHERE u.email = ?1")
    public UserEntity findByEmail(String email);

}
