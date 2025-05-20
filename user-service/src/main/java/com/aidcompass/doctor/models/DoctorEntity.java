package com.aidcompass.doctor.models;

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



@Entity
@Table(name = "doctors")
@NamedEntityGraph(
        name = "doctor.specializations",
        attributeNodes = @NamedAttributeNode("specializations")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_specialization_relations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<DoctorSpecializationEntity> specializations;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", nullable = false, updatable = false, unique = true)
    private DetailEntity detailEntity;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    // каким-то образом синхронизировать с сервисом контактов
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatusEntity;

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
