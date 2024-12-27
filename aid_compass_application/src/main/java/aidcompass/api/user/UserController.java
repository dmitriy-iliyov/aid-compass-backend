package aidcompass.api.user;


import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.general.utils.MapUtils;
import aidcompass.api.user.models.dto.UserUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Validator validator;
    private final MessageSource messageSource;


    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto,
                                        BindingResult bindingResult, Locale locale){

        if(bindingResult.hasErrors()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    this.messageSource.getMessage("400", null, "error.400", locale));
            problemDetail.setProperty("errors", MapUtils.bindingErrors(bindingResult));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        userService.save(userRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserRegistrationDto userRegistrationDto,
                                        @PathVariable("id") @Positive Long id, Locale locale){

        if (!userService.existingById(id))
            throw new EntityNotFoundException();
        UserUpdateDto userUpdateDto = userService.mapToUpdateDto(userRegistrationDto);
        userUpdateDto.setId(id);

        Set<ConstraintViolation<UserUpdateDto>> bindingResult = validator.validate(userUpdateDto);
        if(!bindingResult.isEmpty()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    this.messageSource.getMessage("400", null, "error.400", locale));
            problemDetail.setProperty("error", MapUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        userService.update(userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") @Positive Long id){
        UserResponseDto userResponseDto = userService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return users.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email){
        userService.deleteByEmail(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}