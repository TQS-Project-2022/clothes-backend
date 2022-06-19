package pt.ua.clothesbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.clothesbackend.model.ERole;
import pt.ua.clothesbackend.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
