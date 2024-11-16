package aidcompass.api.doctor;

import aidcompass.api.doctor.models.dto.DoctorRegistrationDto;
import aidcompass.api.doctor.models.dto.DoctorResponseDto;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;
import aidcompass.api.general.utils.ControllerUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final Validator validator;


    @PostMapping("/{id}")
    public ResponseEntity<?> createDoctor(@RequestBody @Valid DoctorRegistrationDto doctorRegistrationDto,
                                          BindingResult bindingResult,
                                          @PathVariable Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Create doctor");
        if (bindingResult.hasErrors()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(httpHeaders)
                    .body(ControllerUtils.bindingErrors(bindingResult));
        }
        try {
            doctorService.save(doctorRegistrationDto, id);
        } catch (Exception e) {
            log.error("Error occurred while saving the doctor: ", e);
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

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveDoctor(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Approving doctor");
        try{
            doctorService.approve(id);
        } catch (Exception e){
            log.error("Error occurred while approving the doctor: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getDoctor(@PathVariable String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Get doctor by username");
        DoctorResponseDto doctorResponseDto;
        try{
            doctorResponseDto = doctorService.findByUsername(username);
        } catch (Exception e){
            log.error("Error occurred while getting the doctor: ", e);
            if (e instanceof EntityNotFoundException)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(httpHeaders)
                        .build();
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
        return doctorResponseDto == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).build()
                : ResponseEntity.ok().headers(httpHeaders).body(doctorResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@RequestBody DoctorRegistrationDto doctorRegistrationDto,
                                          @PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-info", "Updating doctor");
        if (!doctorService.existingById(id))
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(httpHeaders)
                    .build();
        DoctorUpdateDto doctorUpdateDto = doctorService.mapToUpdateDto(doctorRegistrationDto);
        doctorUpdateDto.setId(id);
        try {
            Set<ConstraintViolation<DoctorUpdateDto>> bindingResult = validator.validate(doctorUpdateDto);
            if(!bindingResult.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(httpHeaders)
                        .body(ControllerUtils.bindingErrorsFromConstraintValidatorContext(bindingResult));
            }
        } catch (Exception e){
            log.error("Error occurred while validate doctor: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        try{
            doctorService.update(doctorUpdateDto);
        }catch (Exception e){
            log.error("Error occurred while update the doctor: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .build();
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapprovedDoctors(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Get unapproved doctors");
        List<DoctorResponseDto> doctorResponseDtoList;
        try {
            doctorResponseDtoList = doctorService.findAllUnapproved();
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(doctorResponseDtoList);
    }

    @GetMapping("/approved/{specialization}")
    public ResponseEntity<?> getDoctorsBySpecialization(@PathVariable String specialization){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Get doctors by specialization");
        List<DoctorResponseDto> doctorResponseDtoList;
        try{
            doctorResponseDtoList = doctorService.findAllApprovedBySpecialization(specialization);
        }catch (Exception e){
            log.error("Error occurred while getting doctors by specialization: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(doctorResponseDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Deleting doctor by id");
        try {
            doctorService.deleteById(id);
        }catch (Exception e){
            log.error("Error occurred while deleting doctor: ", e);

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