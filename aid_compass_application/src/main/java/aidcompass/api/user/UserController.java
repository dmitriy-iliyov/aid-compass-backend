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

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Creating user");

        if(bindingResult.hasErrors()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    this.messageSource.getMessage("400", null, "error.400", locale));
            problemDetail.setProperty("errors", MapUtils.bindingErrors(bindingResult));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(httpHeaders)
                    .body(problemDetail);
        }
        try{
            userService.save(userRegistrationDto);
//            httpHeaders.setLocation(URI.create("/customer/login"));
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(httpHeaders)
                    .build();
        }catch (Exception e){
            log.error("Error occurred while creating the user: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserRegistrationDto userRegistrationDto,
                                        @PathVariable @Positive Long id, Locale locale){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Updating user");
        if (!userService.existingById(id))
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(httpHeaders)
                    .build();
        UserUpdateDto userUpdateDto = userService.mapToUpdateDto(userRegistrationDto);
        userUpdateDto.setId(id);
        try {
            Set<ConstraintViolation<UserUpdateDto>> bindingResult = validator.validate(userUpdateDto);
            if(!bindingResult.isEmpty()){
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage("400", null, "error.400", locale));
                problemDetail.setProperty("error", MapUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(httpHeaders)
                        .body(problemDetail);
            }
        } catch (Exception e){
            log.error("Error occurred while validate user: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        try{
            userService.update(userUpdateDto);
        } catch (Exception e){
            log.error("Error occurred while updating the user: ", e);
            if(e instanceof EntityNotFoundException)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .headers(httpHeaders)
                        .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable @Positive Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Get user by username");
        UserResponseDto userResponseDto;
        try{
            userResponseDto = userService.findById(id);
        } catch (Exception e){
            log.error("Error occurred while getting user: ", e);
            if(e instanceof EntityNotFoundException)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .headers(httpHeaders)
                        .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(userResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Getting all users");
        List<UserResponseDto> users;
        try{
            users = userService.findAll();
        } catch (Exception e){
            log.error("Error occurred while getting all users: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return users.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null)
                : ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(users);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Deleting user");
        try{
            userService.deleteByEmail(email);
        } catch (Exception e){
            log.error("Error occurred while deleting the user: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .headers(httpHeaders)
                .build();
    }

}