package com.aidcompass.user.controllers;


import com.aidcompass.clients.confirmation.ConfirmationService;
import com.aidcompass.user.models.dto.UserRegistrationDto;
import com.aidcompass.user.models.dto.UserResponseDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.user.services.UserFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        userFacade.save(userRegistrationDto);
        confirmationService.sendConfirmationMessage(userRegistrationDto.email());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "A confirmation email has been sent to your email address." +
                        "Please check your inbox to complete registration."
                );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userFacade.findById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updatedUser(@PathVariable("id") UUID id,
                                         @RequestBody UserUpdateDto userUpdateDto,
                                         @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody) {
        UserResponseDto response = userFacade.update(id, userUpdateDto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id) {
        userFacade.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/user/{id}/{password}")
    public ResponseEntity<?> deleteUserByPassword(@PathVariable("id") UUID id,
                                                  @PathVariable("password")
                                                  @NotBlank(message = "Password can't be empty or blank!")
                                                  @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
                                                  String password) {
        userFacade.deleteByPassword(id, password);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

//    @Operation(description = "Get all users")
//    @GetMapping
//    public ResponseEntity<?> getAllUsers(@RequestParam(value = "filter", required = false) UserFilterDto filter,
//                                         @RequestParam(value = "page", defaultValue = "0")
//                                         @PositiveOrZero(message = "Page should be positive!") int page,
//                                         @RequestParam(value = "size", defaultValue = "10")
//                                         @Positive(message = "Size should be positive!") int size) {
//        PagedDataDto<PublicUserResponseDto> users = userFacade.findAll(filter, PageRequest.of(page, size));
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(users);
//    }
}