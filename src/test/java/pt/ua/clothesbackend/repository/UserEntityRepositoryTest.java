package pt.ua.clothesbackend.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.clothesbackend.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class UserEntityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;


    @Test
    @DisplayName("If a user is persisted, then the repository must find it by email")
    void whenInsertUser_FindByEmail(){
        String email = "renan@email.com";
        UserEntity renanFerreira = new UserEntity(
                "Renan", "Ferreira", email, "password");
        entityManager.persistAndFlush(renanFerreira);

        UserEntity renanReturned = repository.findByEmail(email);
        assertThat( renanReturned ).isEqualTo(renanFerreira);

    }

    @Test
    @DisplayName("If a invalid user is requested by email, it returns null")
    void whenRequestedInvalidUser_thenReturnNull(){

        UserEntity fromDB = repository.findByEmail("it is not an email");
        assertThat( fromDB ).isNull();

    }

    @Test
    @DisplayName("When insert a certain number of users, then must return all")
    void whenInsertUsers_whenFindAll_thenReturnAll(){
        UserEntity renan = new UserEntity(
                "Renan", "Ferreira", "renan@email.com", "password");
        UserEntity thiago = new UserEntity(
                "Thiago", "Brasil", "brasiln@email.com", "password");
        UserEntity adam = new UserEntity(
                "Adam", "Komo", "adam@email.com", "password");

        entityManager.persist(renan);
        entityManager.persist(thiago);
        entityManager.persist(adam);
        entityManager.flush();

        List<UserEntity> userEntities = repository.findAll();

        assertThat(userEntities).hasSize(3)
                .extracting(UserEntity::getUserId)
                .containsOnly(renan.getUserId(), thiago.getUserId(), adam.getUserId());

    }

}
