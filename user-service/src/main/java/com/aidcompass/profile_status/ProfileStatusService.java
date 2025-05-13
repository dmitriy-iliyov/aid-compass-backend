package com.aidcompass.profile_status;


import com.aidcompass.exceptions.ProfileStatusEntityNotFoundByStatusException;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusDto;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileStatusService {

    private final ProfileStatusRepository repository;
    private final ProfileStatusMapper mapper;


    @Transactional
    public List<ProfileStatusDto> saveAll(List<ProfileStatus> statusList) {
        List<ProfileStatusEntity> entityList = mapper.toEntityList(statusList);
        return mapper.toDtoList(repository.saveAll(entityList));
    }

    @Named("toStatusEntity")
    @Cacheable(value = "profile_status", key = "#status.toString()")
    @Transactional(readOnly = true)
    public ProfileStatusEntity findByStatus(ProfileStatus status) {
        return repository.findByStatus(status).orElseThrow(ProfileStatusEntityNotFoundByStatusException::new);
    }
}
