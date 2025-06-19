package com.aidcompass.jurist.specialization;

import com.aidcompass.jurist.specialization.models.JuristType;
import com.aidcompass.jurist.specialization.models.JuristTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JuristTypeRepository extends JpaRepository<JuristTypeEntity, Long> {
    Optional<JuristTypeEntity> findByType(JuristType type);
}
