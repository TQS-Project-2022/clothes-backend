package pt.ua.clothesbackend.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
public class SecurityConfiguration {

    //@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
             (authz) -> authz.anyRequest().authenticated()
        ).httpBasic(withDefaults());
        return http.build();
    }



}
