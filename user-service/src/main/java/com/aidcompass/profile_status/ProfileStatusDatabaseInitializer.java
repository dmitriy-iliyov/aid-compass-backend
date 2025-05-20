package com.aidcompass.profile_status;

import com.aidcompass.profile_status.models.ProfileStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileStatusDatabaseInitializer {

    private final ProfileStatusService service;


    @PostConstruct
    public void setUpStatuses() {
        service.saveAll(Arrays.stream(ProfileStatus.values()).toList());
    }
}