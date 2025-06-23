package com.aidcompass.jurist.models;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.Gender;
import com.aidcompass.detail.models.GenderConverter;
import com.aidcompass.jurist.specialization.models.JuristSpecializationEntity;
import com.aidcompass.jurist.specialization.models.JuristTypeEntity;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jurists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"typeEntity", "specializations", "profileStatusEntity", "detailEntity"})
@EqualsAndHashCode(exclude = {"typeEntity", "specializations", "profileStatusEntity", "detailEntity"})
public class JuristEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private JuristTypeEntity typeEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jurist_specialization_relations",
            joinColumns = @JoinColumn(name = "jurist_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<JuristSpecializationEntity> specializations;

    @Column(name = "specialization_detail", nullable = false)
    private String specializationDetail;

    @Column(name = "working_experience", nullable = false, length = 2)
    private Integer workingExperience;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatusEntity;

    @Column(name = "profile_progress", nullable = false)
    private int profileProgress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", nullable = false, updatable = false, unique = true)
    private DetailEntity detailEntity;

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