package com.aidcompass.profile_status.models;


import com.aidcompass.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;
}
