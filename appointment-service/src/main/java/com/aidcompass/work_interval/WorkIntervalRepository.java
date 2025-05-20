package com.aidcompass.work_interval;

import com.aidcompass.work_interval.models.WorkIntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkIntervalRepository extends JpaRepository<WorkIntervalEntity, Long> {

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<WorkIntervalEntity> findAllByDate(LocalDate date);

    @Query("""
              SELECT i FROM WorkIntervalEntity i
              WHERE i.ownerId = :owner_id
              AND i.date >= :start
              AND i.date <= :end
    """)
    List<WorkIntervalEntity> findAllByOwnerIdAndDateInterval(@Param("owner_id") UUID ownerId,
                                                             @Param("start") LocalDate start,
                                                             @Param("end") LocalDate end);

    @Query("""
              SELECT w.id FROM WorkIntervalEntity w
              WHERE w.ownerId = :owner_id
    """)
    List<Long> findAllIdsByOwnerId(@Param("owner_id") UUID ownerId);

    @Query("""
              SELECT COUNT(e.id) 
              FROM WorkIntervalEntity e WHERE e.ownerId = :owner_id AND e.id IN :ids
    """)
    long countOwnedByOwnerId(@Param("owner_dd") UUID ownerId, @Param("ids") List<Long> ids);
}
