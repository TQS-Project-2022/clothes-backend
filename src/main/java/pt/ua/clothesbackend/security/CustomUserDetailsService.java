package pt.ua.clothesbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pt.ua.clothesbackend.entity.UserEntity;
import pt.ua.clothesbackend.service.DefaultUserService;

import java.util.ArrayList;
import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DefaultUserService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username;
        Optional<UserEntity> _user = service.getByEmail(email);
        if(_user.isEmpty()) {
            throw new UsernameNotFoundException(
                    String.format("User %s does not exist", email)
            );
        }
        UserEntity user = _user.get();
        User userDetails = new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
