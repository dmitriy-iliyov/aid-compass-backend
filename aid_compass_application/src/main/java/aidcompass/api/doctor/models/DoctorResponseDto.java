package aidcompass.api.doctor.models;


import aidcompass.api.user.models.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto extends UserResponseDto {
        private String licenseNumber;
        private String specialization;
        private Integer yearsOfExperience;
        private String address;
        private String achievements;
        private String profilePictureUrl;
}
