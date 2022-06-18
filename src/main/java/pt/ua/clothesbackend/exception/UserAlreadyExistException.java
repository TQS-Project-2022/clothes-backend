package pt.ua.clothesbackend.exception;

import lombok.Getter;

public class UserAlreadyExistException extends Exception {

    @Getter
    private final String message;

    public UserAlreadyExistException(String email) {
        this.message = String.format("User %s already exists", email);
    }

}
