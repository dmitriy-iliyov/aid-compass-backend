package com.aidcompass.interval.repository;

import com.aidcompass.interval.models.NearestIntervalEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NearestIntervalRepository extends KeyValueRepository<NearestIntervalEntity, UUID> {
}
