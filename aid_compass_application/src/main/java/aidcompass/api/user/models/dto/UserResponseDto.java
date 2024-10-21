package aidcompass.api.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto{
    private Long id;
    private String username;
    private String email;
    private String number;
    private String createdAt;
    private Instant updatedAt;
}
