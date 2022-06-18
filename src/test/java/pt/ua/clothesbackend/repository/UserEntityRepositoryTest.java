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
    @Disabled
    @DisplayName("If a user is persisted, then the repository must find it by id")
    void whenInsertUser_FindById(){
        UserEntity renanFerreira = new UserEntity(
                "Renan", "Ferreira", "renan@email.com", "password");
        long renanId = renanFerreira.getUserId();
        entityManager.persistAndFlush(renanFerreira);

        UserEntity renanReturned = repository.findById(renanId).orElse(null);

        assertThat( renanReturned ).isEqualTo(renanFerreira);

    }

    @Test
    @Disabled
    @DisplayName("If a invalid user is requested by email, it returns null")
    void whenRequestedInvalidUser_thenReturnNull(){

        UserEntity fromDB = repository.findByEmail("it is not an email");
        assertThat( fromDB ).isNull();

    }

    @Test
    @Disabled
    @DisplayName("If a invalid user is requested by id, it returns null")
    void whenRequestedInvalidUserById_thenReturnNull(){

        UserEntity fromDB = repository.findById(-11L).orElse(null);

        assertThat( fromDB ).isNull();

    }

    @Test
    @Disabled
    @DisplayName("If a invalid user is requested by id, it returns null")
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

    @Test
    @Disabled
    @DisplayName("After save a user with a certain email, if tries to save another user with the same email, it is not saved and if second user is requested by id, then return null")
    void whenInsertUsersWithSameEmail_thenSecondUserIsNotPersisted(){
        UserEntity renan = new UserEntity("Renan", "Ferreira", "renan@email.com", "password");
        long renanId = renan.getUserId();
        UserEntity thiago = new UserEntity("Thiago", "Brasil", "renan@email.com", "password");
        long thiagoId = thiago.getUserId();

        entityManager.persist(renan);
        entityManager.persist(thiago);
        entityManager.flush();

        UserEntity thiagoFromDb = repository.findById(thiagoId).orElse(null);

        assertThat(thiagoFromDb).isNull();

    }




}
