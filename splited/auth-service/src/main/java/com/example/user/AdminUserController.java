package com.example.user;

import com.example.user.models.dto.UserResponseDto;
import com.example.user.models.dto.UserUpdateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserFacade userFacade;


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id")
                                         @UUID(message = "Invalid id format!") String id) {
        UserResponseDto userResponseDto = userFacade.findById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatedUserById(@PathVariable("id")
                                             @UUID(message = "Invalid id format!") String id,
                                             @RequestBody UserUpdateDto userUpdateDto) {
        userFacade.update(java.util.UUID.fromString(id), userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //mb add password check
    @DeleteMapping("/{id}/{password}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id")
                                            @UUID(message = "Invalid id format!") String id,
                                            @PathVariable(value = "password", required = false)
                                            @NotBlank(message = "Password shouldn't be empty!") String password) {
        userFacade.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
