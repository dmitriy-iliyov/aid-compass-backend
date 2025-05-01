package com.example.user.models.entity;

import com.example.authority.models.AuthorityEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity{

    @Id
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authrity_id")
    )
    private List<AuthorityEntity> authorities = new ArrayList<>();

//    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private VolunteerBaseEntity volunteer;

//    public UserEntity(String email, String password, String username, String number, Role role) {
//        this.email = email;
//        this.password = password;
//        this.username = username;
//        this.number = number;
//        this.role = role;
//    }

    @PrePersist
    public void beforeCreate(){
        createdAt = Instant.now();
        updatedAt = createdAt;
        isExpired = false;
        isLocked = false;
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt = Instant.now();
    }
}
