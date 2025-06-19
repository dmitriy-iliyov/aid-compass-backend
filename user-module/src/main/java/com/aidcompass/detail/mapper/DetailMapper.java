package com.aidcompass.detail.mapper;


import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface DetailMapper {

    @Named("toPrivateDetailDto")
    PrivateDetailResponseDto toPrivateDetailDto(DetailEntity entity);

    @Named("toPublicDetailDto")
    PublicDetailResponseDto toPublicDetailDto(DetailEntity entity);

    void updateEntityFromDto(DetailDto dto, @MappingTarget DetailEntity entity);
}
