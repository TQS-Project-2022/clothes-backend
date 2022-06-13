package pt.ua.clothesbackend.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class UserData implements Serializable {

    @NotEmpty(message = "First name can not be empty")
    @Getter
    @Setter
    private String firstName;

    @NotEmpty(message = "Last name can not be empty")
    @Getter
    @Setter
    private String lastName;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email id")
    @Getter
    @Setter
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Getter
    @Setter
    private String password;

    public UserData() {}


}
