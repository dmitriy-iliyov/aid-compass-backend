package com.aidcompass.general.database_initalizers;

import com.aidcompass.doctor.specialization.DoctorSpecializationService;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DoctorSpecializationDatabaseInitializer {

    private final DoctorSpecializationService service;


    @PostConstruct
    public void setUpSpecializations() {
        List<DoctorSpecialization> specializationList = new ArrayList<>(List.of(DoctorSpecialization.values()));
        service.saveAll(specializationList);
    }
}
