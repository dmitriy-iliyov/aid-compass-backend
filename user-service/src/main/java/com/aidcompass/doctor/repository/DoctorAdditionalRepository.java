package com.aidcompass.doctor.repository;

import com.aidcompass.doctor.models.DoctorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface DoctorAdditionalRepository {
    Slice<DoctorEntity> findAllWithEntityGraphBySpecification(Specification<DoctorEntity> spec, Pageable pageable);
}
