package com.aidcompass.confirmation;


import com.aidcompass.confirmation.services.AccountResourceConfirmationService;
import com.aidcompass.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/api/confirmations")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ContactConfirmationFacade contactConfFacade;
    private final AccountResourceConfirmationService accountConfService;


    //этот запрос генерирует сервис контактов
    // протестировать @ValidEnum и добавить во всех ендпоинтах
    @Operation(summary = "Request confirmation token.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Token successfully created."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input.")
    })
    @PostMapping("/request")
    public ResponseEntity<?> getToken(@Parameter(description = "resource")
                                      @RequestParam("resource")
                                      @NotBlank(message = "Resource shouldn't be blank or empty!")
                                      String resource,

                                      @Parameter(description = "resource_id")
                                      @RequestParam("resource_id")
                                      @Positive(message = "Resource id should be positive!")
                                      Long resourceId,

                                      @Parameter(description = "resource_type")
                                      @RequestParam("resource_type")
                                      @ValidEnum(enumClass = ResourceType.class, message = "Resource type should be valid!")
                                      ResourceType type) throws Exception {
        contactConfFacade.sendConfirmationMessage(resource, resourceId, type);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    // только для пользователя и не подтвержденного тоже
    @Operation(summary = "Confirm resource by token.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Token successfully validated."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid token or resource type.")
    })
    @PostMapping("/resource")
    public ResponseEntity<?> confirmResource(@Parameter(description = "token")
                                             @RequestParam("token")
                                             @NotBlank(message = "Token shouldn't be blank or empty!")
                                             String token,

                                             @Parameter(description = "resource_type")
                                             @RequestParam("resource_type")
                                             @ValidEnum(enumClass = ResourceType.class, message = "Resource type should be valid!")
                                             ResourceType type) {
        contactConfFacade.validateConfirmationToken(token, type);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    // только внутри системы, этот запос генерирует сервис авторизации
    @Operation(summary = "Request confirmation token for linked email")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Token successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid email format")
    })
    @PostMapping("/linked-email/request")
    public ResponseEntity<?> getTokenForLinkedEmail(@Parameter(description = "email")
                                                    @RequestParam("email")
                                                    @NotBlank(message = "Email shouldn't be blank or empty!")
                                                    @Email(message = "Email should be valid!") String email) throws Exception {
        accountConfService.sendConfirmationMessage(email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    // только для пользователя
    @Operation(
            summary = "Confirm linked email by token",
            description = "Validates the confirmation token for a linked email and redirects the user to the login page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "303", description = "Token successfully validated and user redirected to login"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid token")
    })
    @PostMapping("/linked-email")
    public ResponseEntity<?> confirmLinkedEmail(@Parameter(description = "token")
                                                @RequestParam("token")
                                                @NotBlank(message = "Token shouldn't be blank or empty!") String token) {
        accountConfService.validateConfirmationToken(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/users/login"));
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .headers(httpHeaders)
                .build();
    }
}

