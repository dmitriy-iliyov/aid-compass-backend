package com.aidcompass.interval.repository;

import com.aidcompass.interval.models.IntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntervalRepository extends JpaRepository<IntervalEntity, Long> {

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<IntervalEntity> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    @Query("""
              SELECT i FROM IntervalEntity i
              WHERE i.ownerId = :owner_id
              AND i.date >= :start
              AND i.date <= :end
    """)
    List<IntervalEntity> findAllByOwnerIdAndDateInterval(@Param("owner_id") UUID ownerId,
                                                         @Param("start") LocalDate start,
                                                         @Param("end") LocalDate end);

    @Query("""
              SELECT COUNT(e.id) 
              FROM IntervalEntity e 
              WHERE e.ownerId = :owner_id AND e.id IN :ids
    """)
    long countOwnedByOwnerId(@Param("owner_id") UUID ownerId, @Param("ids") List<Long> ids);

    void deleteAllByOwnerId(UUID ownerId);

    Optional<IntervalEntity> findFirstByOwnerIdAndDateBetweenOrderByDateAscStartAsc(UUID ownerId, LocalDate start, LocalDate end);

    boolean existsByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date);

}
