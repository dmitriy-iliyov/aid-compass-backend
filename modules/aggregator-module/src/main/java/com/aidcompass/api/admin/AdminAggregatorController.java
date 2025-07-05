package com.aidcompass.api.admin;


import com.aidcompass.api.doctor.DoctorAggregatorService;
import com.aidcompass.api.jurist.JuristAggregatorService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/aggregator")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminAggregatorController {

    private final DoctorAggregatorService doctorAggregatorService;
    private final JuristAggregatorService juristAggregatorService;


    @GetMapping("/doctors/unapproved/cards")
    public ResponseEntity<?> getUnapprovedDoctors(@RequestParam(value = "page", defaultValue = "0")
                                                  @PositiveOrZero(message = "Page should be positive!")
                                                  int page,
                                                  @RequestParam(value = "size", defaultValue = "10")
                                                  @Min(value = 10, message = "Size must be at least 10!")
                                                  int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorAggregatorService.findAllUnapproved(page, size));
    }

    @GetMapping("/jurists/unapproved/cards")
    public ResponseEntity<?> getUnapprovedJurists(@RequestParam(value = "page", defaultValue = "0")
                                                  @PositiveOrZero(message = "Page should be positive!")
                                                  int page,
                                                  @RequestParam(value = "size", defaultValue = "10")
                                                  @Min(value = 10, message = "Size must be at least 10!")
                                                  int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristAggregatorService.findAllUnapproved(page, size));
    }

    @GetMapping("/doctors/unapproved/cards/names")
    public ResponseEntity<?> getUnapprovedDoctorsByNames(@RequestParam(value = "first_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "First name should contain only Ukrainian!"
                                                         )
                                                         String firstName,

                                                         @RequestParam(value = "second_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "Second name should contain only Ukrainian!"
                                                         )
                                                         String secondName,

                                                         @RequestParam(value = "last_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "Last name should contain only Ukrainian!"
                                                         )
                                                         String lastName,

                                                         @RequestParam(value = "page", defaultValue = "0")
                                                         @PositiveOrZero(message = "Page should be positive!")
                                                         int page,
                                                         @RequestParam(value = "size", defaultValue = "10")
                                                         @Min(value = 10, message = "Size must be at least 10!")
                                                         int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorAggregatorService.findAllUnapprovedByNamesCombination(firstName, secondName, lastName, page, size));
    }

    @GetMapping("/jurists/unapproved/cards/names")
    public ResponseEntity<?> getUnapprovedJuristsByNames(@RequestParam(value = "first_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "First name should contain only Ukrainian!"
                                                         )
                                                         String firstName,

                                                         @RequestParam(value = "second_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "Second name should contain only Ukrainian!"
                                                         )
                                                         String secondName,

                                                         @RequestParam(value = "last_name", required = false)
                                                         @Size(min = 2, max = 20,
                                                                 message = "Should has lengths from 2 to 20 characters!")
                                                         @Pattern(
                                                                 regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                 message = "Last name should contain only Ukrainian!"
                                                         )
                                                         String lastName,

                                                         @RequestParam(value = "page", defaultValue = "0")
                                                         @PositiveOrZero(message = "Page should be positive!")
                                                         int page,
                                                         @RequestParam(value = "size", defaultValue = "10")
                                                         @Min(value = 10, message = "Size must be at least 10!")
                                                         int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristAggregatorService.findAllUnapprovedByNamesCombination(firstName, secondName, lastName, page, size));
    }
}