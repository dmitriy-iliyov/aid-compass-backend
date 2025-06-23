package com.aidcompass.repositories;

import com.aidcompass.models.entity.ContinueFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinueFlagRepository extends JpaRepository<ContinueFlagEntity, Integer> {
}
