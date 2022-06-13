package pt.ua.clothesbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.clothesbackend.data.UserData;
import pt.ua.clothesbackend.entity.User;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;
import pt.ua.clothesbackend.service.UserService;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Nullable
    public ResponseEntity<User> register(@RequestBody UserData userData) {
        try {
            userService.register(userData);
            Optional<User> user = userService.getByEmail(userData.getEmail());
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
