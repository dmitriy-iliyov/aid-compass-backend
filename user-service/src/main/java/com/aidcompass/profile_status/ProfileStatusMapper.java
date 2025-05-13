package com.aidcompass.profile_status;

import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusDto;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProfileStatusMapper {

    ProfileStatusEntity toEntity(ProfileStatus status);

    List<ProfileStatusEntity> toEntityList(List<ProfileStatus> statusList);

    ProfileStatusDto toDto(ProfileStatusEntity entity);

    List<ProfileStatusDto> toDtoList(List<ProfileStatusEntity> profileStatusEntities);
}
