package com.aidcompass.appointment;


import com.aidcompass.appointment.models.AppointmentEntity;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>,
                                               JpaSpecificationExecutor<AppointmentEntity> {

    List<AppointmentEntity> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date,
                                                                 AppointmentStatus status);

    @Query("""
        SELECT a FROM AppointmentEntity a
        WHERE a.volunteerId = :volunteerId
        AND a.date BETWEEN :start AND :end
    """)
    List<AppointmentEntity> findAllByVolunteerIdAndDateInterval(@Param("volunteerId") UUID volunteerId,
                                                                @Param("start") LocalDate start,
                                                                @Param("end") LocalDate end);

    @Query("""
        SELECT a FROM AppointmentEntity a
        WHERE a.customerId = :customerId
        AND a.date BETWEEN :start AND :end
    """)
    List<AppointmentEntity> findAllByCustomerIdAndDateInterval(@Param("customerId") UUID customerId,
                                                               @Param("start") LocalDate start,
                                                               @Param("end") LocalDate end);

    @Modifying
    @Query("""
        DELETE FROM AppointmentEntity a
        WHERE a.customerId = :participantId
           OR a.volunteerId = :participantId
    """)
    void deleteAllByParticipantId(@Param("participantId") UUID participantId);

    @Modifying
    @Query("""
        UPDATE AppointmentEntity a
        SET a.status = :status, a.review = :review
        WHERE a.id = :id
    """)
    void updateStatusAndSetReview(@Param("id") Long id, @Param("review") String review,
                                  @Param("status") AppointmentStatus status);

    @Modifying
    @Query("""
        UPDATE AppointmentEntity a
        SET a.status = :status
        WHERE a.id = :id
    """)
    void updateStatus(Long id, AppointmentStatus status);

    boolean existsByCustomerIdAndDateAndStartAndStatus(UUID customerId, LocalDate date,
                                                       LocalTime start, AppointmentStatus status);

    @Modifying
    @Query("""
        UPDATE AppointmentEntity a
        SET a.status = :status
        WHERE a.volunteerId = : participantId
        OR a.customerId = :participantId
        AND a.date = :date
    """)
    void updateAllStatus(UUID participantId, LocalDate date, AppointmentStatus status);
}