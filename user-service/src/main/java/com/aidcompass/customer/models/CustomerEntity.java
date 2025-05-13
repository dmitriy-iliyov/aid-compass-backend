package com.aidcompass.customer.models;


import com.aidcompass.profile_status.models.ProfileStatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class CustomerEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatus;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public CustomerEntity(UUID id, ProfileStatusEntity profileStatus) {
        this.id = id;
        this.profileStatus = profileStatus;
    }

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