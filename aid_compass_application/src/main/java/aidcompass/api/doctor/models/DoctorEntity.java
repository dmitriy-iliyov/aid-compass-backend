package aidcompass.api.doctor.models;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.general.models.VolunteerBaseEntity;
import aidcompass.api.user.models.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class DoctorEntity extends VolunteerBaseEntity {

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

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<DoctorAppointmentEntity> appointments;

    public DoctorEntity(String email, String username, String number, boolean approved, Instant createdAt, UserEntity user,
                        String licenseNumber, String specialization, int yearsOfExperience, String address, String achievements) {
        this.setEmail(email);
        this.setUsername(username);
        this.setNumber(number);
        this.setApproved(approved);
        this.setCreatedAt(createdAt);
        this.setUser(user);
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.address = address;
        this.achievements = achievements;
    }
}
