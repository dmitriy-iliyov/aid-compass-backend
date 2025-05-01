package com.aidcompass.auth.general;

import com.aidcompass.auth.user.models.entity.UserEntity;
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
public abstract class VolunteerBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "number", unique = true, length = 17)
    private String number;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

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