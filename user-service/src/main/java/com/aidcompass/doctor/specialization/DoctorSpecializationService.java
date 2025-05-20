package com.aidcompass.doctor.specialization;

import com.aidcompass.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.exceptions.DoctorSpecializationEntityNotFoundBySpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorSpecializationService {

    private final DoctorSpecializationRepository repository;
    private final DoctorSpecializationMapper mapper;


    @Transactional
    public void saveAll(List<DoctorSpecialization> specializationList) {
        List<DoctorSpecializationEntity> entityList = mapper.toEntityList(specializationList);
        repository.saveAll(entityList);
    }

    @Cacheable(value = "doctor:spec", key = "#specialization.toString()")
    @Transactional(readOnly = true)
    public DoctorSpecializationEntity findEntityBySpecialization(DoctorSpecialization specialization) {
        return repository.findBySpecialization(specialization).orElseThrow(
                DoctorSpecializationEntityNotFoundBySpecializationException::new
        );
    }
}
