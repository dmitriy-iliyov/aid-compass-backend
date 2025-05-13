package com.aidcompass.doctor.services;


import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import com.aidcompass.doctor.DoctorRepository;
import com.aidcompass.doctor.mapper.DoctorMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UnifiedDoctorService implements DoctorService, PersistDoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;


    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID id, DoctorRegistrationDto dto, DetailEntity detail) {
        DoctorEntity doctorEntity = mapper.toEntity(id, dto);
        return mapper.toPrivateDto(repository.save(doctorEntity));
    }

    @Transactional
    @Override
    public String setAddress(UUID id, String address) {
        repository.updateAddressById(id, address);
        return address;
    }

    @Transactional
    @Override
    public PrivateDoctorResponseDto update(UUID id, DoctorUpdateDto doctorUpdateDto) {
        DoctorEntity entity = repository.findWithSepcsById(id).orElseThrow(DoctorNotFoundByIdException::new);
        mapper.updateEntityFromUpdateDto(doctorUpdateDto, entity);
        return mapper.toPrivateDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void approve(UUID id) {
        repository.approveById(id, true);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateDoctorResponseDto findPrivateById(UUID id) {
        return mapper.toPrivateDto(repository.findWithSepcsById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PublicDoctorResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findWithSepcsById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrivateDoctorResponseDto> findAllUnapproved() {
        return mapper.toPrivateDtoList(repository.findAllByApproved(false));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicDoctorResponseDto> findAllApproved() {
        return mapper.toPublicDtoList(repository.findAllByApproved(true));
    }

//    @Transactional(readOnly = true)
//    public List<PublicDoctorResponseDto> findAllApprovedBySpecialization(DoctorSpecialization specialization) {
//        List<PublicDoctorResponseDto> dtoList = mapper.toPublicDtoList(repository.findAllByApprovedAndSpecialization(true, specialization));
//        return dtoList;
//    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
