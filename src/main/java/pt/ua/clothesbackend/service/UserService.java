package pt.ua.clothesbackend.service;

import pt.ua.clothesbackend.dto.CredentialsDTO;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;
import pt.ua.clothesbackend.exception.UserDoesNotExist;

import java.util.Optional;

public interface UserService {
    public void register(UserDTO user) throws UserAlreadyExistException;
    public Boolean userExists(String email);
    public Optional<UserEntity> getByEmail(String email);

    public boolean checkCredentials(CredentialsDTO credentialsDTO) throws UserDoesNotExist;
}
