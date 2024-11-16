package aidcompass.api.doctor.appointment;

import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
import aidcompass.api.general.utils.ControllerUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor-appointments")
public class DoctorAppointmentController {

    private final DoctorAppointmentServices doctorAppointmentServices;
    private final Validator validator;


    @PostMapping("")
    public ResponseEntity<?> createDoctorAppointment(@RequestBody DoctorAppointmentRegistrationDto
                                                                 doctorAppointmentRegistrationDto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Creating appointment to doctor");
        if(!doctorAppointmentServices.existingByDoctorNUserId(doctorAppointmentRegistrationDto.getUserId(),
                doctorAppointmentRegistrationDto.getDoctorId()))
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(httpHeaders)
                    .build();
        Set<ConstraintViolation<DoctorAppointmentRegistrationDto>> bindingResult = validator.validate(doctorAppointmentRegistrationDto);
        if(!bindingResult.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(httpHeaders)
                    .body(ControllerUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
        }
        try{
            doctorAppointmentServices.save(doctorAppointmentRegistrationDto);
        } catch (Exception e){
            log.error("Error occurred while creating appointment to doctor: ", e);
            if (e instanceof IllegalArgumentException)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(httpHeaders)
                        .body(e.getMessage());
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
    public ResponseEntity<?> getDoctorAppointment(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Get doctor appointment by id");
        DoctorAppointmentResponseDto doctorAppointmentResponseDto;
        try {
            doctorAppointmentResponseDto = doctorAppointmentServices.findById(id);
        } catch (Exception e){
            log.error("Error occurred while getting appointment to doctor: ", e);
            if (e instanceof EntityNotFoundException)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .headers(httpHeaders)
                        .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return doctorAppointmentResponseDto == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).build()
                : ResponseEntity.ok().headers(httpHeaders).body(doctorAppointmentResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDoctorAppointmentsById(@RequestBody DoctorAppointmentUpdateDto
                                                                      doctorAppointmentUpdateDto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Updating appointment by id");
        if(!doctorAppointmentServices.existingByDoctorNUserId(doctorAppointmentUpdateDto.getUserId(),
                doctorAppointmentUpdateDto.getVolunteerId()))
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(httpHeaders)
                    .build();
        Set<ConstraintViolation<DoctorAppointmentUpdateDto>> bindingResult = validator.validate(doctorAppointmentUpdateDto);
        if(!bindingResult.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(httpHeaders)
                    .body(ControllerUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
        }
        try{
            doctorAppointmentServices.update(doctorAppointmentUpdateDto);
        } catch (Exception e){
            log.error("Error occurred while creating appointment to doctor: ", e);
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

    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getAllDoctorAppointmentsByDoctorId(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Creating appointment to doctor");
        List<DoctorAppointmentResponseDto> doctorAppointmentResponseDtoList;
        try{
            doctorAppointmentResponseDtoList = doctorAppointmentServices.findAllByDoctorId(id);
        } catch (Exception e){
            log.error("Error occurred while creating appointment to doctor: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(doctorAppointmentResponseDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorAppointment(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Creating appointment to doctor");
        try{
            doctorAppointmentServices.deleteById(id);
        } catch (Exception e){
            log.error("Error occurred while deleting appointment to doctor: ", e);
            if (e instanceof EntityNotFoundException)
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
                .status(HttpStatus.NO_CONTENT)
                .headers(httpHeaders)
                .build();
    }
}
