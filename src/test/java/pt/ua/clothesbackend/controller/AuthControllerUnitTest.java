package pt.ua.clothesbackend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.service.DefaultUserService;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DefaultUserService service;


    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenRegisterUser_thenReturnUser() throws Exception {
        UserDTO userDTO = new UserDTO("john", "doe", "password", "john@email.pt");
        UserEntity user = new UserEntity(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        );
        when( service.getByEmail(user.getEmail()) ).thenReturn(Optional.of(user));

        mvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(toJson(userDTO))

        ).andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.email").value(user.getEmail())
        );

        verify(service, times(1)).getByEmail(user.getEmail());


    }

    private byte[] toJson(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(obj);
    }


}
