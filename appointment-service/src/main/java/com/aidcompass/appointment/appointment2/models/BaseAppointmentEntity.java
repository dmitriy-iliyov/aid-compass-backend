package com.aidcompass.appointment.models;

import com.aidcompass.general.VolunteerBaseEntity;
import com.aidcompass.user.models.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseAppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "appointment_date", nullable = false, unique = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant appointmentDate;

    @Column(name = "topic", nullable = false, length = 40)
    private String topic;

    @Column(name = "description", nullable = false, length = 80)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id", nullable = false)
    private VolunteerBaseEntity volunteer;


    @PrePersist
    public void beforePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt = Instant.now();
    }
}

