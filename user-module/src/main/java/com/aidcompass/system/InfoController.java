package com.aidcompass.system;


import com.aidcompass.detail.models.Gender;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristType;
import com.aidcompass.profile_status.models.ProfileStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/info/v1/")
public class InfoController {

    @GetMapping("/jurists-specialization")
    public ResponseEntity<?> getJuristSpecialization() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Arrays.stream(JuristSpecialization.values()).map(JuristSpecialization::getTranslate));
    }

    @GetMapping("/jurists-type")
    public ResponseEntity<?> getJuristType() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Arrays.stream(JuristType.values()).map(JuristType::getTranslate));
    }

    @GetMapping("/doctors-specialization")
    public ResponseEntity<?> getDoctorSpecialization() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Arrays.stream(DoctorSpecialization.values()).map(DoctorSpecialization::getTranslate));
    }

    @GetMapping("/profile-status")
    public ResponseEntity<?> getProfileStatus() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProfileStatus.values());
    }

    @GetMapping("/genders")
    public ResponseEntity<?> getGenders() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Arrays.stream(Gender.values()).map(Gender::getTranslate));
    }
}
