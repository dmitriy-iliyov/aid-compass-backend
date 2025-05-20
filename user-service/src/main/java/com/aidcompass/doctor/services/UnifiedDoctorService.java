package com.aidcompass.doctor.services;


import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.mapper.FullDoctorMapper;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.page.PageDto;
import com.aidcompass.doctor.repository.DoctorRepository;
import com.aidcompass.doctor.mapper.DoctorMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.doctor.repository.DoctorSpecification;
import com.aidcompass.doctor.specialization.DoctorSpecializationService;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.profile_status.ProfileStatusService;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UnifiedDoctorService implements DoctorService, PersistDoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final FullDoctorMapper fullMapper;
    private final ProfileStatusService profileStatusService;
    private final DoctorSpecializationService specializationService;
    private final CacheManager cache;


    @CacheEvict(value = "doctor:unaprv", allEntries = true)
    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID id, DoctorRegistrationDto dto, DetailEntity detail) {
        DoctorEntity doctorEntity = mapper.toEntity(id, dto, specializationService);
        ProfileStatusEntity statusEntity = profileStatusService.findByStatus(ProfileStatus.INCOMPLETE);
        doctorEntity.setProfileStatusEntity(statusEntity);
        return mapper.toPrivateDto(repository.save(doctorEntity));
    }

    @CacheEvict(value = "doctor:public:full", key = "#id")
    @Transactional
    @Override
    public PrivateDoctorResponseDto update(UUID id, DoctorUpdateDto doctorUpdateDto) {
        DoctorEntity entity = repository.findWithSpecsAndProfileStatusById(id).orElseThrow(DoctorNotFoundByIdException::new);
        mapper.updateEntityFromUpdateDto(doctorUpdateDto, entity, specializationService);
        entity = repository.save(entity);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE) {
            PublicDoctorResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctor:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "doctor:names"),
                    @CacheEvict(value = "doctor:aprv"),
                    @CacheEvict(value = "doctor:unaprv"),
                    @CacheEvict(value = "doctor:specs")
            }
    )
    @Transactional
    @Override
    public void approve(UUID id) {
        repository.approveById(id);
    }

    @Cacheable(
            value = "doctor:names",
            key="#firstName + ':' + #secondName + ':' + #lastName + ':' + #page.size() + ':' + #page.page()",
            condition = "#page.size() == 10 && #page.page() <= 5"
    )
    @Transactional(readOnly = true)
    @Override
    public List<PublicDoctorResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName, PageDto page) {
        DoctorSpecification specification = new DoctorSpecification(firstName, secondName, lastName);
        Slice<DoctorEntity> slice = repository.findAllWithEntityGraphBySpecification(specification, Pageable.ofSize(page.size()).withPage(page.page()));
        return mapper.toPublicDtoList(slice.getContent());
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateDoctorResponseDto findPrivateById(UUID id) {
        DoctorEntity entity = repository.findWithSpecsAndProfileStatusById(id).orElseThrow(DoctorNotFoundByIdException::new);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE) {
            PublicDoctorResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctor:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Cacheable(value = "doctor:public", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public PublicDoctorResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findWithSpecsById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public FullPrivateDoctorResponseDto findFullPrivateById(UUID id) {
        DoctorEntity entity = repository.findFullById(id).orElseThrow();
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE) {
            FullPublicDoctorResponseDto publicDto = fullMapper.toFullPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctor:public:full")).put(id, publicDto);
        }
        return fullMapper.toFullPrivateDto(entity);
    }

    @Cacheable(value = "doctor:public:full", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public FullPublicDoctorResponseDto findFullPublicById(UUID id) {
        DoctorEntity entity = repository.findWithSpecsAndDetailById(id).orElseThrow();
        return fullMapper.toFullPublicDto(entity);
    }

    //сортировать по созданию
    @Cacheable(
            value = "doctor:unaprv",
            key = "#page.size() + ':' + #page.page()",
            condition = "#page.size() == 10 && #page.page() <= 5"
    )
    @Transactional(readOnly = true)
    @Override
    public List<PrivateDoctorResponseDto> findAllUnapproved(PageDto page) {
        Slice<DoctorEntity> slice = repository.findAllByApprovedFalse(
                Pageable.ofSize(page.size()).withPage(page.page())
        );
        return mapper.toPrivateDtoList(slice.getContent());
    }

    @Cacheable(
            value = "doctor:aprv",
            key = "#page.size() + ':' + #page.page()",
            condition = "#page.size() == 10 && #page.page() <= 5"
    )
    @Transactional(readOnly = true)
    @Override
    public List<PublicDoctorResponseDto> findAllApproved(PageDto page) {
        Slice<DoctorEntity> slice = repository.findAllByApprovedTrue(
                Pageable.ofSize(page.size()).withPage(page.page())
        );
        return mapper.toPublicDtoList(slice.getContent());
    }

    @Cacheable(
            value = "doctor:specs",
            key = "#page.size() + ':' + #page.page()",
            condition = "#page.size() == 10 && #page.page() <= 5"
    )
    @Transactional(readOnly = true)
    @Override
    public List<PublicDoctorResponseDto> findAllApprovedBySpecialization(DoctorSpecialization specialization, PageDto page) {
        DoctorSpecializationEntity entity = specializationService.findEntityBySpecialization(specialization);
        Slice<DoctorEntity> slice = repository.findAllBySpecialization(
                entity,
                Pageable.ofSize(page.size()).withPage(page.page())
        );
        return mapper.toPublicDtoList(slice.getContent());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "doctor:public", key = "#id"),
                    @CacheEvict(value = "doctor:public:full", key = "#id"),
                    @CacheEvict(value = "doctor:names"),
                    @CacheEvict(value = "doctor:aprv"),
                    @CacheEvict(value = "doctor:unaprv"),
                    @CacheEvict(value = "doctor:specs")
            }
    )
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
