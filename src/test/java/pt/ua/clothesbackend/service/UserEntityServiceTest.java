package pt.ua.clothesbackend.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.repository.UserRepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DefaultUserService service;


    @Test
    @DisplayName("When There is a valid user, return it by his email")
    void whenExistsUser_thenReturnsByEmail() {
        String email = "jon@email.pt";
        UserEntity john = new UserEntity("john", "doe", email, "passwword");

        Mockito.when(repository.findByEmail(john.getEmail())).thenReturn(john);

        Optional<UserEntity> found = service.getByEmail(john.getEmail());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getEmail()).isEqualTo(email);

        Mockito.verify(repository, VerificationModeFactory.times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("When There is a valid user, return true if exists")
    void whenExistsUser_thenReturnsTrue() {
        String email = "jon@email.pt";
        UserEntity john = new UserEntity("john", "doe", email, "passwword");

        Mockito.when(repository.existsByEmail(john.getEmail())).thenReturn(true);

        boolean found = service.userExists(john.getEmail());
        assertThat(found).isTrue();

        Mockito.verify(repository, VerificationModeFactory.times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("When calls user with invalid email, return null")
    void whenNotExistsUser_thenReturnsNull() {
        String email = "notexist@email.pt";

        Mockito.when(repository.findByEmail(email)).thenReturn(null);

        Optional<UserEntity> found = service.getByEmail(email);
        assertThat(found.isEmpty()).isTrue();

        Mockito.verify(repository, VerificationModeFactory.times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("When check user that not exists, return false")
    void whenNotExistsUser_thenReturnsFalse() {
        String email = "jon@email.pt";

        Mockito.when(repository.existsByEmail(email)).thenReturn(false);

        boolean found = service.userExists(email);
        assertThat(found).isFalse();

        Mockito.verify(repository, VerificationModeFactory.times(1)).existsByEmail(email);
    }

}
