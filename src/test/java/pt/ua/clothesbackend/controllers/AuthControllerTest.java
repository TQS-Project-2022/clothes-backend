package pt.ua.clothesbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.clothesbackend.dto.LoginRequest;
import pt.ua.clothesbackend.dto.RegisterRequest;
import pt.ua.clothesbackend.repositories.UserRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class AuthControllerTest {
    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql")
            .withDatabaseName("database")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }

    @Test
    public void givenRegisterRequest_whenRegister_thenReturnMessageResponse() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user1");
        registerRequest.setPassword("pass1");

        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User registered successfully!")));

    }

    @Test
    public void givenRegisterRequest_whenRegisterWithUsernameTaken_thenReturnBadRequest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user1");
        registerRequest.setPassword("pass1");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void givenLoginRequest_whenLogin_thenReturnJwtResponse() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user1");
        registerRequest.setPassword("pass1");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("pass1");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(loginRequest.getUsername())));
    }

    @Test
    public void givenLoginRequest_whenLoginWithNonExistingUsername_thenReturnUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nonExistingUser");
        loginRequest.setPassword("pass1");


        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isUnauthorized());
    }

    @Test
    public void givenLoginRequest_whenLoginWithBadPassword_thenReturnUnauthorized() throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user1");
        registerRequest.setPassword("pass1");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("badPassword");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));


        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isUnauthorized());
    }

}