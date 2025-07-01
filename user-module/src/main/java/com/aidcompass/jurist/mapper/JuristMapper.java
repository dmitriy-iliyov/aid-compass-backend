package com.aidcompass.jurist.mapper;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.dto.BasePrivateVolunteerDto;
import com.aidcompass.jurist.models.JuristEntity;
import com.aidcompass.jurist.models.JuristDto;
import com.aidcompass.jurist.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.specialization.mapper.JuristSpecializationMapper;
import com.aidcompass.jurist.specialization.mapper.JuristTypeMapper;
import com.aidcompass.jurist.specialization.JuristSpecialization;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {JuristTypeMapper.class, JuristSpecializationMapper.class, DetailMapper.class}
)
public interface JuristMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "gender", expression = "java(com.aidcompass.enums.gender.Gender.toEnum(dto.getGender()))")
    @Mapping(target = "typeEntity", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    JuristEntity toEntity(UUID id, JuristDto dto);

    @Mapping(target = "isApproved", source = "approved")
    @Mapping(target = "profileStatus", source = "profileStatusEntity.profileStatus")
    @Mapping(target = "profileProgress",
            expression = "java((int) (100 * ((double) entity.getProfileProgress() / com.aidcompass.profile_status.ProfileConfig.MAX_PROFILE_PROGRESS)))")
    BasePrivateVolunteerDto toBaseDto(JuristEntity entity);

    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "type", source = "typeEntity.type")
    @Mapping(target = "specializations", source = "specializations")
    PrivateJuristResponseDto toPrivateDto(JuristEntity entity);

    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "type", source = "entity.typeEntity.type")
    @Mapping(target = "specializations", source = "paramSpecializations")
    PrivateJuristResponseDto toPrivateDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity);

    @Mapping(target = "type", source = "typeEntity.type")
    @Mapping(target = "specializations", source = "specializations")
    PublicJuristResponseDto toPublicDto(JuristEntity entity);

    @Mapping(target = "type", source = "entity.typeEntity.type")
    @Mapping(target = "specializations", source = "paramSpecializations")
    PublicJuristResponseDto toPublicDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", expression = "java(com.aidcompass.enums.gender.Gender.toEnum(dto.getGender()))")
    @Mapping(target = "typeEntity", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    void updateEntityFromUpdateDto(JuristDto dto, @MappingTarget JuristEntity entity);
}
