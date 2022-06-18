package pt.ua.clothesbackend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.clothesbackend.dto.CredentialsDTO;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;
import pt.ua.clothesbackend.exception.UserDoesNotExist;
import pt.ua.clothesbackend.repository.UserRepository;

import java.util.Optional;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDTO userDTO) throws UserAlreadyExistException {
        //Let's check if user already registered with us
        if(userExists(userDTO.getEmail())){
            throw new UserAlreadyExistException(userDTO.getEmail());
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.setPassword(encodePassword(userDTO.getPassword()));
        repository.save(userEntity);
    }

    @Override
    public boolean checkCredentials(CredentialsDTO credentialsDTO) throws UserDoesNotExist {
        String email = credentialsDTO.getEmail();
        if(this.userExists(email)) {
            UserEntity user = repository.findByEmail(email);
            return user.getPassword().equals(encodePassword(credentialsDTO.getPassword()));
        } else {
            throw new UserDoesNotExist(credentialsDTO.getEmail());
        }
    }

    @Override
    public Boolean userExists(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return Optional.of(repository.findByEmail(email));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
