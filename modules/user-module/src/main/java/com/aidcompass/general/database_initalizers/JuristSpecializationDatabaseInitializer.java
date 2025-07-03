package com.aidcompass.general.database_initalizers;

import com.aidcompass.jurist.specialization.JuristSpecializationService;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class JuristSpecializationDatabaseInitializer {

    private final JuristSpecializationService service;


    @PostConstruct
    public void setUpSpecializations() {
        List<JuristSpecialization> specializationList = new ArrayList<>(List.of(JuristSpecialization.values()));
        service.saveAll(specializationList);
    }
}
