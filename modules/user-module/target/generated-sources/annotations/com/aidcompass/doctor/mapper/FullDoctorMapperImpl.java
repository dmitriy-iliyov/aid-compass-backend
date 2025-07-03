package com.aidcompass.doctor.mapper;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.PrivateDetailResponseDto;
import com.aidcompass.detail.models.PublicDetailResponseDto;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class FullDoctorMapperImpl implements FullDoctorMapper {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public FullPrivateDoctorResponseDto toFullPrivateDto(DoctorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PrivateDetailResponseDto detail = null;

        detail = detailEntityToPrivateDetailResponseDto( entity.getDetailEntity() );

        PrivateDoctorResponseDto doctor = doctorMapper.toPrivateDto(entity);

        FullPrivateDoctorResponseDto fullPrivateDoctorResponseDto = new FullPrivateDoctorResponseDto( doctor, detail );

        return fullPrivateDoctorResponseDto;
    }

    @Override
    public FullPrivateDoctorResponseDto toFullPrivateDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        PrivateDetailResponseDto detail = null;
        if ( entity != null ) {
            detail = detailEntityToPrivateDetailResponseDto( entity.getDetailEntity() );
        }

        PrivateDoctorResponseDto doctor = doctorMapper.toPrivateDto(paramSpecializations, entity);

        FullPrivateDoctorResponseDto fullPrivateDoctorResponseDto = new FullPrivateDoctorResponseDto( doctor, detail );

        return fullPrivateDoctorResponseDto;
    }

    @Override
    public FullPublicDoctorResponseDto toFullPublicDto(DoctorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PublicDoctorResponseDto doctor = null;
        PublicDetailResponseDto detail = null;

        doctor = doctorMapper.toPublicDto( entity );
        detail = detailEntityToPublicDetailResponseDto( entity.getDetailEntity() );

        FullPublicDoctorResponseDto fullPublicDoctorResponseDto = new FullPublicDoctorResponseDto( doctor, detail );

        return fullPublicDoctorResponseDto;
    }

    protected PrivateDetailResponseDto detailEntityToPrivateDetailResponseDto(DetailEntity detailEntity) {
        if ( detailEntity == null ) {
            return null;
        }

        Long id = null;
        String aboutMyself = null;
        String address = null;

        id = detailEntity.getId();
        aboutMyself = detailEntity.getAboutMyself();
        address = detailEntity.getAddress();

        PrivateDetailResponseDto privateDetailResponseDto = new PrivateDetailResponseDto( id, aboutMyself, address );

        return privateDetailResponseDto;
    }

    protected PublicDetailResponseDto detailEntityToPublicDetailResponseDto(DetailEntity detailEntity) {
        if ( detailEntity == null ) {
            return null;
        }

        String aboutMyself = null;
        String address = null;

        aboutMyself = detailEntity.getAboutMyself();
        address = detailEntity.getAddress();

        PublicDetailResponseDto publicDetailResponseDto = new PublicDetailResponseDto( aboutMyself, address );

        return publicDetailResponseDto;
    }
}
