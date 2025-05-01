package com.aidcompass.auth.user;

import com.aidcompass.auth.user.models.dto.UserResponseDto;
import com.aidcompass.auth.user.models.dto.UserUpdateDto;
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
                                             @RequestBody UserUpdateDto userUpdateDto,
                                             @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody) {
        UserResponseDto response = userFacade.update(java.util.UUID.fromString(id), userUpdateDto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id")
                                            @UUID(message = "Invalid id format!") String id) {
        userFacade.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
