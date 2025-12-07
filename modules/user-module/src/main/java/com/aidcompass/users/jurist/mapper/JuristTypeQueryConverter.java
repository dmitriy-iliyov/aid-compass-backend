package com.aidcompass.users.jurist.mapper;

import com.aidcompass.users.jurist.specialization.models.JuristType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JuristTypeQueryConverter implements Converter<String, JuristType> {
    @Override
    public JuristType convert(String source) {
        if (source == null) {
            return null;
        }
        return JuristType.toEnum(source);
    }
}
