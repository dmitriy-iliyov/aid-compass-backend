package com.aidcompass.doctor.specialization;

import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecializationEntity, Integer> {
    Optional<DoctorSpecializationEntity> findBySpecialization(DoctorSpecialization specialization);

    @Query("""
        SELECT ds FROM DoctorSpecializationEntity ds
        JOIN FETCH ds.doctors d
        WHERE d.id IN :ids
    """)
    List<DoctorSpecializationEntity> findAllByDoctorIds(@Param("ids") List<UUID> ids);
}
