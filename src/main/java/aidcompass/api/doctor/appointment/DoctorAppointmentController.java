package aidcompass.api.doctor.appointment;

import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
import aidcompass.api.general.utils.MapUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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
@RequiredArgsConstructor
@RequestMapping("/doctor-appointments")
public class DoctorAppointmentController {

    private final DoctorAppointmentServices doctorAppointmentServices;
    private final Validator validator;
    private final MessageSource messageSource;


    @PostMapping("")
    public ResponseEntity<?> createDoctorAppointment(@RequestBody DoctorAppointmentRegistrationDto
                                                                 doctorAppointmentRegistrationDto, Locale locale){

        Set<ConstraintViolation<DoctorAppointmentRegistrationDto>> bindingResult = validator.validate(doctorAppointmentRegistrationDto);
        if(!bindingResult.isEmpty()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("400", new Object[0], "error.400",locale));
            problemDetail.setProperty("errors", MapUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        doctorAppointmentServices.save(doctorAppointmentRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorAppointment(@PathVariable("id") @Positive Long id){
        DoctorAppointmentResponseDto doctorAppointmentResponseDto = doctorAppointmentServices.findById(id);
        return ResponseEntity.ok(doctorAppointmentResponseDto);
    }

    @PatchMapping("")
    public ResponseEntity<?> updateDoctorAppointmentsById(@RequestBody DoctorAppointmentUpdateDto
                                                                      doctorAppointmentUpdateDto, Locale locale){

        if(!doctorAppointmentServices.existingByDoctorNUserId(doctorAppointmentUpdateDto.getUserId(),
                doctorAppointmentUpdateDto.getVolunteerId()))
            throw new EntityNotFoundException("User or doctor not found.");

        Set<ConstraintViolation<DoctorAppointmentUpdateDto>> bindingResult = validator.validate(doctorAppointmentUpdateDto);
        if(!bindingResult.isEmpty()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("400", null, "error.appointment.400", locale));
            problemDetail.setProperty("error", MapUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        doctorAppointmentServices.update(doctorAppointmentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getAllDoctorAppointmentsByDoctorId(@PathVariable("id") @Positive Long id){
        List<DoctorAppointmentResponseDto> doctorAppointmentResponseDtoList;
        doctorAppointmentResponseDtoList = doctorAppointmentServices.findAllByDoctorId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorAppointmentResponseDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorAppointment(@PathVariable("id") @Positive Long id){
        doctorAppointmentServices.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
