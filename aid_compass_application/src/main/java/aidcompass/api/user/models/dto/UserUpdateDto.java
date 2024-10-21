package aidcompass.api.user.models.dto;

import aidcompass.api.general.models.CustomBindingErrors;
import aidcompass.api.general.validation.PhoneNumber;
import aidcompass.api.user.validation.user_update.ValidUserUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidUserUpdate
public class UserUpdateDto extends CustomBindingErrors {

    private Long id;

    @NotBlank(message = "Username shouldn't be empty or blank!")
    @Size(min = 10, max = 30, message = "Username length must be greater than 10 and less than 30!")
    private String username;

    @NotBlank(message = "Email shouldn't be empty or blank!")
    @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "Password can't be empty or blank!")
    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
    private String password;

    @NotBlank(message = "Number can't be empty or blank!")
    @Size(min = 13, max = 17, message = "Number should has valid lengths!")
    @PhoneNumber
    private String number;
}
