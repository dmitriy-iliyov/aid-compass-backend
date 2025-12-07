package com.aidcompass.users.jurist.mapper;

import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JuristSpecializationQueryConverter implements Converter<String, JuristSpecialization> {
    @Override
    public JuristSpecialization convert(String source) {
        return JuristSpecialization.toEnum(source);
    }
}
