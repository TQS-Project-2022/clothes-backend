package pt.ua.clothesbackend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.clothesbackend.dto.UserDTO;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.exception.UserAlreadyExistException;
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
            throw new UserAlreadyExistException();
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        encodePassword(userEntity, userDTO);
        repository.save(userEntity);

    }

    @Override
    public Boolean userExists(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return repository.findByEmail(email);
    }

}
