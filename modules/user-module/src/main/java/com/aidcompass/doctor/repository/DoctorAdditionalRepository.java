package com.aidcompass.doctor.repository;

import com.aidcompass.doctor.models.DoctorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface DoctorAdditionalRepository {
    Page<DoctorEntity> findAllByNamesCombination(Specification<DoctorEntity> spec, Pageable pageable);

    Page<DoctorEntity> findAllUnapprovedByNamesCombination(Specification<DoctorEntity> spec, Pageable pageable);
}
