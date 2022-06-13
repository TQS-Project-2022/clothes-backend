package pt.ua.clothesbackend.service;

import pt.ua.clothesbackend.data.UserData;
import pt.ua.clothesbackend.entity.User;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;

import java.util.Optional;

public interface UserService {
    public void register(UserData user) throws UserAlreadyExistException;
    public Boolean userExists(String email);

    public Optional<User> getByEmail(String email);
}
