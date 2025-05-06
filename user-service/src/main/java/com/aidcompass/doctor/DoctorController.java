package com.aidcompass.doctor;


import com.aidcompass.doctor.models.dto.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.DoctorUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final Validator validator;
    private final MessageSource messageSource;


    @PostMapping("/{userId}")
    public ResponseEntity<?> createDoctor(@PathVariable("userId") @Positive Long userId,
                                          @RequestBody @Valid DoctorRegistrationDto doctorRegistrationDto) {
        doctorService.save(doctorRegistrationDto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveDoctor(@PathVariable("id") @Positive Long id) {
        doctorService.approve(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getDoctor(@PathVariable("username") String username) {
        DoctorResponseDto doctorResponseDto = doctorService.findByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@RequestBody DoctorRegistrationDto doctorRegistrationDto,
                                          @PathVariable("id") @Positive Long id, Locale locale) {

        if (!doctorService.existingById(id)) {
            throw new EntityNotFoundException();
        }

        DoctorUpdateDto doctorUpdateDto = doctorService.mapToUpdateDto(doctorRegistrationDto);
        doctorUpdateDto.setId(id);

        Set<ConstraintViolation<DoctorUpdateDto>> bindingResult = validator.validate(doctorUpdateDto);
        if(!bindingResult.isEmpty()) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("400", null, "error.doctor.400", locale));
            problemDetail.setProperty("error", MapUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        doctorService.update(doctorUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getUnapprovedDoctors() {
        List<DoctorResponseDto> doctorResponseDtoList = doctorService.findAllUnapproved();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorResponseDtoList);
    }

    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedDoctors() {
        List<DoctorResponseDto> doctorResponseDtoList = doctorService.findAllApproved();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorResponseDtoList);
    }

    @GetMapping("/approved/{specialization}")
    public ResponseEntity<?> getDoctorsBySpecialization(
            @PathVariable("specialization") @Pattern(regexp = "^[a-zA-z]+$]") String specialization) {
        List<DoctorResponseDto> doctorResponseDtoList = doctorService.findAllApprovedBySpecialization(specialization);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorResponseDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable("id") @Positive Long id) {
        doctorService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}