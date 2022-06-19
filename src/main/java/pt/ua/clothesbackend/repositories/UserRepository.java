package pt.ua.clothesbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.clothesbackend.model.MyUser;

import java.util.Optional;

public interface UserRepository extends CrudRepository<MyUser, Integer> {
    Optional<MyUser> findByUsername(String username);
    Boolean existsByUsername(String username);
}
