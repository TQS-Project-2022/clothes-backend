package pt.ua.clothesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {

    @NotNull
    @NotEmpty
    @Email
    @Getter
    @Setter
    private String email;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String password;

}
