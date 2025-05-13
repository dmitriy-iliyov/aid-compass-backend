package com.aidcompass.detail.mapper;


import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface DetailMapper {

    DetailEntity toEntity(UUID id, UUID userId, DetailDto dto);

    PrivateDetailResponseDto toPrivateResponseDto(DetailEntity entity);

    PublicDetailResponseDto toPublicResponseDto(DetailEntity entity);

    void updateEntityFromDto(DetailDto dto, @MappingTarget DetailEntity entity);
}
