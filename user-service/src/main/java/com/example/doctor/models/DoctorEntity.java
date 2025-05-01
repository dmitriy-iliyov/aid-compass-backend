package com.example.doctor.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    private UUID id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="second_name", nullable = false)
    private String secondName;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "years_of_experience", length = 2)
    private Integer yearsOfExperience;

    @Column(name = "address")
    private String address;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void beforeCreate(){
        createdAt = Instant.now();
        updatedAt = createdAt;
        approved = false;
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt = Instant.now();
    }
}
