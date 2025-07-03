package com.aidcompass.detail;

import com.aidcompass.detail.models.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, UUID> {

    Optional<DetailEntity> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
