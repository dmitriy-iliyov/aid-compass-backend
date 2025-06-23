package com.aidcompass.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IntervalRepository extends JpaRepository<Object, Long> {

    @Modifying
    @Query(value = """
        WITH to_delete AS (
            SELECT id
            FROM work_intervals
            WHERE date < :weekStart
            LIMIT :batchSize
        )
        DELETE FROM work_intervals
        WHERE id IN (SELECT id FROM to_delete)
        RETURNING id
    """, nativeQuery = true)
    List<Long> deleteBatchBeforeDate(int batchSize, LocalDate weakStart);
}
