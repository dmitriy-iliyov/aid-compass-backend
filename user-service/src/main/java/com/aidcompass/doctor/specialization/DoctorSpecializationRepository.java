package com.aidcompass.doctor.specialization;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecializationEntity, Integer> {
    Optional<DoctorSpecializationEntity> findBySpecialization(DoctorSpecialization specialization);
}
