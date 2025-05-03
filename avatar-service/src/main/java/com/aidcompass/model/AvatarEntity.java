package com.aidcompass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "avatars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", unique = true)
    private UUID userId;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    // фабрику из auth service
    public AvatarEntity(UUID userId, String uri) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.uri = uri;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
