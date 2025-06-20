package com.aidcompass.doctor.models.dto.doctor;

import com.aidcompass.base_dto.BaseVolunteerRegistrationDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Set;

@Getter
public class DoctorDto extends BaseVolunteerRegistrationDto {

    @NotEmpty(message = "Should contain at least one specialization!")
    @Valid
    private final Set<@ValidEnum(enumClass = DoctorSpecialization.class,
                                 message = "Unsupported doctor specialization!") String> specializations;


    @JsonCreator
    public DoctorDto(@JsonProperty("last_name") String lastName,
                     @JsonProperty("first_name") String firstName,
                     @JsonProperty("second_name") String secondName,
                     @JsonProperty("gender") String gender,
                     Set<String> specializations,
                     @JsonProperty("specialization_detail") String specializationDetail,
                     @JsonProperty("working_experience") Integer workingExperience) {
        super(lastName, firstName, secondName, gender, specializationDetail, workingExperience);
        this.specializations = specializations;
    }
}
