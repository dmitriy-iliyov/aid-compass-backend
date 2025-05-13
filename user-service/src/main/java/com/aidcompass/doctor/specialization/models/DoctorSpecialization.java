package com.aidcompass.doctor.specialization.models;

import com.aidcompass.exceptions.doctor.InvalidDoctorSpecializationTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum DoctorSpecialization {
    ALLERGOLOGIST,
    ANDROLOGIST,
    ANESTHESIOLOGIST,
    CARDIOLOGIST,
    DENTIST,
    DERMATOLOGIST,
    ENDOCRINOLOGIST,
    FORENSIC_EXPERT,
    GASTROENTEROLOGIST,
    GENERAL_PRACTITIONER,
    GYNECOLOGIST,
    HEMATOLOGIST,
    IMMUNOLOGIST,
    INFECTIOUS_DISEASE_SPECIALIST,
    NEPHROLOGIST,
    NEUROLOGIST,
    NEUROSURGEON,
    NUTRITIONIST,
    OBSTETRICIAN,
    OCCUPATIONAL_THERAPIST,
    ONCOLOGIST,
    OPHTHALMOLOGIST,
    ORTHODONTIST,
    ORTHOPEDIST,
    OTOLARYNGOLOGIST,
    PATHOLOGIST,
    PEDIATRICIAN,
    PERIODONTIST,
    PLASTIC_SURGEON,
    PROCTOLOGIST,
    PSYCHIATRIST,
    PSYCHOLOGIST,
    PSYCHOTHERAPIST,
    PULMONOLOGIST,
    RADIOLOGIST,
    REHABILITATION_SPECIALIST,
    RHEUMATOLOGIST,
    SPEECH_THERAPIST,
    SPORTS_MEDICINE_SPECIALIST,
    SURGEON,
    UROLOGIST;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public DoctorSpecialization toDoctorSpecialization(String stringSpecialization) {
        return Arrays.stream(DoctorSpecialization.values())
                .filter(doctorSpecializationType -> doctorSpecializationType.toString().equals(stringSpecialization))
                .findFirst()
                .orElseThrow(InvalidDoctorSpecializationTypeException::new);
    }
}
