package aidcompass.api.user.models;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.general.models.VolunteerEntitySuperclass;
import aidcompass.api.security.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "number", unique = true, length = 17)
    private String number;

    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedAt;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private VolunteerEntitySuperclass volunteer;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<DoctorAppointmentEntity> appointmentsToDoctor;

    public UserEntity(String email, String password, String username, String number, Role role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.number = number;
        this.role = role;
    }

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
