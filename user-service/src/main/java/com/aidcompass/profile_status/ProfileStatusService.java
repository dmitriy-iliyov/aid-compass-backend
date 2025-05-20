package com.aidcompass.profile_status;


import com.aidcompass.exceptions.ProfileStatusEntityNotFoundByStatusException;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusDto;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileStatusService {

    private final ProfileStatusRepository repository;
    private final ProfileStatusMapper mapper;


    @Transactional
    public List<ProfileStatusDto> saveAll(List<ProfileStatus> statusList) {
        log.info(statusList.toString());
        List<ProfileStatusEntity> entityList = mapper.toEntityList(statusList);
        log.info(entityList.toString());
        return mapper.toDtoList(repository.saveAll(entityList));
    }

    @Cacheable(value = "profile:status", key = "#status.toString()")
    @Transactional(readOnly = true)
    public ProfileStatusEntity findByStatus(ProfileStatus status) {
        return repository.findByProfileStatus(status).orElseThrow(ProfileStatusEntityNotFoundByStatusException::new);
    }
}
