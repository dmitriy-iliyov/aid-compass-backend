package com.aidcompass.auth.user.models.dto;

import com.aidcompass.auth.authority.models.Authority;
import com.aidcompass.auth.user.validation.user_update.UserUpdate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@UserUpdate
public class SystemUserUpdateDto {

    @NotNull(message = "Id shouldn't be null!")
    @org.hibernate.validator.constraints.UUID(message = "Invalid id format!")
//    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
//             message = "Invalid id format!")
    private UUID id;

    @NotBlank(message = "Email shouldn't be empty or blank!")
    @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "Password can't be empty or blank!")
    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
    private String password;

//    @NotBlank(message = "Number can't be empty or blank!")
//    @Size(min = 13, max = 17, message = "Number should has valid lengths!")
//    @PhoneNumber
//    private String number;

    private List<Authority> authorities;
}
