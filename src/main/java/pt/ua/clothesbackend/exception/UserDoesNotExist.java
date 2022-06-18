package pt.ua.clothesbackend.exception;

import lombok.Getter;

public class UserDoesNotExist extends Exception {

    @Getter
    private final String message;

    public UserDoesNotExist(String email) {
        this.message = String.format("User %s, does not exist", email);
    }
}
