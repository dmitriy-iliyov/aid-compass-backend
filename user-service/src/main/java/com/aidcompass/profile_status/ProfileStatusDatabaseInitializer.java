package com.aidcompass.profile_status;

import com.aidcompass.profile_status.models.ProfileStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileStatusDatabaseInitializer {

    private final ProfileStatusService service;


    @PostConstruct
    public void setUpStatuses() {
        List<ProfileStatus> statusList = new ArrayList<>(List.of(ProfileStatus.values()));
        service.saveAll(statusList);
    }
}