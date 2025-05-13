package com.aidcompass.doctor.models;

import com.aidcompass.day.DayEntity;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    // искать по айди а не по енаму специализации
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_specializations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<DoctorSpecializationEntity> specializations;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", nullable = false, updatable = false, unique = true)
    private DetailEntity detail;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatus;

    // удалять при удалении доктора
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_timetable",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id")
    )
    private List<DayEntity> days;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}
