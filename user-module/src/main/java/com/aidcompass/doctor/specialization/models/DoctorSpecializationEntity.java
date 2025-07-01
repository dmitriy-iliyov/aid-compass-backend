package com.aidcompass.doctor.specialization.models;


import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.specialization.DoctorSpecialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "doctor_specializations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSpecializationEntity {

    @Id
    @SequenceGenerator(name = "doc_spec_seq", sequenceName = "doc_spec_seq", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_spec_seq")
    private Integer id;

    @Column(name = "specialization", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private DoctorSpecialization specialization;

    @ManyToMany(mappedBy = "specializations", fetch = FetchType.LAZY)
    private Set<DoctorEntity> doctors;
}
