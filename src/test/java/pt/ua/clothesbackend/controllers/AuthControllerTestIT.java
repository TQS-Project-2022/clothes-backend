package pt.ua.clothesbackend.controllers;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class AuthControllerTestIT {

    @Container
    public static MySQLContainer container = new MySQLContainer()
            .withDatabaseName("database")
            .withUsername("root")
            .withPassword("root");

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository repository;

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }

    @Test
    void WhenValidInput_ThenCreateUser() {
        UserDTO userDTO = new UserDTO("john", "doe", "password", "john@email.pt");
        ResponseEntity<UserEntity> response = restTemplate.postForEntity("/auth/register", userDTO, UserEntity.class);

        List<UserEntity> users = repository.findAll();

        assertThat(users).extracting(UserEntity::getEmail).containsOnly(userDTO.getEmail());
    }
}
