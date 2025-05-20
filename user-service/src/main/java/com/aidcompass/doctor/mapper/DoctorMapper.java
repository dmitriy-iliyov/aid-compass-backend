package com.aidcompass.doctor.mapper;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.DoctorSpecializationService;
import com.aidcompass.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import io.swagger.v3.oas.annotations.info.Contact;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DoctorSpecializationMapper.class, DetailMapper.class}
)
public interface DoctorMapper {

    @Mapping(target = "specializations", source = "dto.specializations", qualifiedByName = "toSpecializationsEntityList")
    DoctorEntity toEntity(UUID id, DoctorRegistrationDto dto, @Context DoctorSpecializationService service);

    @Named("toPrivateDto")
    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "toEnumList")
//    @Mapping(target = "profileStatus", expression = "java(entity.getProfileStatus().getStatus().toString())")
    @Mapping(target = "profileStatus", source = "profileStatusEntity.profileStatus")
    PrivateDoctorResponseDto toPrivateDto(DoctorEntity entity);

    List<PrivateDoctorResponseDto> toPrivateDtoList(List<DoctorEntity> entityList);

    @Named("toPublicDto")
    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "toEnumList")
    PublicDoctorResponseDto toPublicDto(DoctorEntity entity);

    List<PublicDoctorResponseDto> toPublicDtoList(List<DoctorEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "toSpecializationsEntityList")
    void updateEntityFromUpdateDto(DoctorUpdateDto dto, @MappingTarget DoctorEntity entity, @Context DoctorSpecializationService service);

    @Named("toSpecializationsEntityList")
    default List<DoctorSpecializationEntity> toSpecializationsEntityList(List<DoctorSpecialization> specializationList, @Context DoctorSpecializationService service) {
        List<DoctorSpecializationEntity> specEntityList = new ArrayList<>();
        for (DoctorSpecialization spec: specializationList) {
            specEntityList.add(service.findEntityBySpecialization(spec));
        }
        return specEntityList;
    }
}
