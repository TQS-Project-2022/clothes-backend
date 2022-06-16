package pt.ua.clothesbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;
import pt.ua.clothesbackend.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Nullable
    public ResponseEntity<UserEntity> register(@RequestBody @Valid UserDTO userDTO) {
        try {

            userService.register(userDTO);
            Optional<UserEntity> user = userService.getByEmail(userDTO.getEmail());
            if (user.isPresent()) {
                HttpStatus status = HttpStatus.CREATED;
                return new ResponseEntity<>(user.get(), status);
            } else {
                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                return new ResponseEntity<>(null, status);
            }
        } catch (UserAlreadyExistException e) {
            HttpStatus status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(null, status);
        }
    }


}
