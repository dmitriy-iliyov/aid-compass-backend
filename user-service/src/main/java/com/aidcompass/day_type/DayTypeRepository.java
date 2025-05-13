package com.aidcompass.day_type;

import com.aidcompass.day_type.models.DayType;
import com.aidcompass.day_type.models.DayTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayTypeRepository extends JpaRepository<DayTypeEntity, Integer> {

    Optional<DayTypeEntity> findByDayType(DayType dayType);
}
