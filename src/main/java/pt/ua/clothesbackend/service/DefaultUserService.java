package pt.ua.clothesbackend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.clothesbackend.data.UserData;
import pt.ua.clothesbackend.entity.User;
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
    public void register(UserData user) throws UserAlreadyExistException {
        //Let's check if user already registered with us
        if(userExists(user.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        repository.save(userEntity);

    }

    @Override
    public Boolean userExists(String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.of(repository.findByEmail(email));
    }

    private void encodePassword(User userEntity, UserData user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
