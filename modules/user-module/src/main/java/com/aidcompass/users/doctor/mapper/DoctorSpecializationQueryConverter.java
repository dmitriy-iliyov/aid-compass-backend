package com.aidcompass.users.doctor.mapper;

import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DoctorSpecializationQueryConverter implements Converter<String, DoctorSpecialization> {
    @Override
    public DoctorSpecialization convert(String source) {
        return DoctorSpecialization.toEnum(source);
    }
}
