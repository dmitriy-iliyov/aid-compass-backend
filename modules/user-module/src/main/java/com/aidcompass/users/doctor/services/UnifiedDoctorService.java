package com.aidcompass.users.doctor.services;


import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.doctor.mapper.DoctorMapper;
import com.aidcompass.users.doctor.mapper.FullDoctorMapper;
import com.aidcompass.users.doctor.models.DoctorEntity;
import com.aidcompass.users.doctor.models.dto.*;
import com.aidcompass.users.doctor.repository.DoctorNamesCombination;
import com.aidcompass.users.doctor.repository.DoctorRepository;
import com.aidcompass.users.doctor.specialization.DoctorSpecializationService;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.general.dto.NameFilter;
import com.aidcompass.users.general.exceptions.doctor.DoctorNotFoundByIdException;
import com.aidcompass.users.general.exceptions.doctor.FullDoctorNotFoundException;
import com.aidcompass.users.profile_status.ProfileConfig;
import com.aidcompass.users.profile_status.ProfileStatusService;
import com.aidcompass.users.profile_status.ProfileStatusUpdateService;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UnifiedDoctorService implements DoctorService, ProfileStatusUpdateService {

    private final ServiceType type = ServiceType.DOCTOR_SERVICE;
    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final FullDoctorMapper fullMapper;
    private final ProfileStatusService profileStatusService;
    private final DoctorSpecializationService specializationService;
    private final CacheManager cache;


    @Override
    public ServiceType getType() {
        return type;
    }

    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID id, DetailEntity detail, DoctorDto dto) {
        ProfileStatusEntity profileStatus = profileStatusService.findByStatus(ProfileStatus.INCOMPLETE);
        List<DoctorSpecializationEntity> specializationEntityList =
                specializationService.findEntityListBySpecializationList(dto.getSpecializations());
        DoctorEntity doctor = mapper.toEntity(id, specializationEntityList, dto);
        doctor.setDetailEntity(detail);
        doctor.setProfileStatusEntity(profileStatus);
        doctor.setProfileProgress(ProfileConfig.PROFILE_PROGRESS_STEP);
        return mapper.toPrivateDto(repository.save(doctor));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "doctors:public", key = "#id",
                                condition = "#result == T(com.aidcompass.users.profile_status.ProfileConfig).MAX_PROFILE_PROGRESS"),
                    @CacheEvict(value = "doctors:public:full", key = "#id",
                                condition = "#result == T(com.aidcompass.users.profile_status.ProfileConfig).MAX_PROFILE_PROGRESS")
            }
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateProfileStatus(UUID id) {
        DoctorEntity entity = repository.findWithProfileStatusById(id).orElseThrow(DoctorNotFoundByIdException::new);
        int newProgress = entity.getProfileProgress() + ProfileConfig.PROFILE_PROGRESS_STEP;
        if (newProgress == ProfileConfig.MAX_PROFILE_PROGRESS) {
            ProfileStatusEntity profileStatus = profileStatusService.findByStatus(ProfileStatus.COMPLETE);
            repository.updateProgressAndProfileStatus(id, ProfileConfig.PROFILE_PROGRESS_STEP,profileStatus);
        } else if (newProgress < ProfileConfig.MAX_PROFILE_PROGRESS) {
            repository.updateProfileProgress(id, ProfileConfig.PROFILE_PROGRESS_STEP);
        }
        return newProgress;
    }

    @CacheEvict(value = "doctors:public:full", key = "#id")
    @Transactional
    @Override
    public PrivateDoctorResponseDto update(UUID id, DoctorDto dto) {
        DoctorEntity entity = repository.findWithSpecsAndProfileStatusById(id).orElseThrow(DoctorNotFoundByIdException::new);
        List<DoctorSpecializationEntity> specializationEntityList =
                specializationService.findEntityListBySpecializationList(dto.getSpecializations());
        mapper.updateEntityFromUpdateDto(specializationEntityList, dto, entity);
        entity = repository.save(entity);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            PublicDoctorResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctors:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateDoctorResponseDto findPrivateById(UUID id) {
        DoctorEntity entity = repository.findWithSpecsAndProfileStatusById(id).orElseThrow(DoctorNotFoundByIdException::new);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            PublicDoctorResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctors:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Cacheable(value = "doctors:public", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public PublicDoctorResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findWithSpecsById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public FullPrivateDoctorResponseDto findFullPrivateById(UUID id) {
        DoctorEntity entity = repository.findWithAllById(id).orElseThrow(FullDoctorNotFoundException::new);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            FullPublicDoctorResponseDto publicDto = fullMapper.toFullPublicDto(entity);
            Objects.requireNonNull(cache.getCache("doctors:public:full")).put(id, publicDto);
        }
        return fullMapper.toFullPrivateDto(entity);
    }

    @Cacheable(value = "doctors:public:full", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public FullPublicDoctorResponseDto findFullPublicById(UUID id) {
        DoctorEntity entity = repository.findWithSpecsAndDetailById(id).orElseThrow(FullDoctorNotFoundException::new);
        return fullMapper.toFullPublicDto(entity);
    }

    @Cacheable(value = "doctors:count")
    @Transactional(readOnly = true)
    @Override
    public long countByIsApproved(boolean approved) {
        return repository.countByIsApproved(approved);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicDoctorResponseDto> findAllByIdIn(Set<UUID> ids) {
        List<DoctorEntity> entityList = repository.findAllByIdIn(ids);
        return toPublicDtoList(
                entityList,
                loadSpecializations(new PageImpl<>(entityList))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullPrivateDoctorResponseDto> findAllUnapproved(com.aidcompass.core.general.contracts.dto.PageRequest page) {
        Page<DoctorEntity> entityPage = repository.findAllByApprovedFalse(
                PageRequest.of(page.getPage(), page.getSize(), Sort.by("createdAt").descending())
        );
        return new PageResponse<>(
                this.toFullPrivateDto(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullPrivateDoctorResponseDto> findAllUnapprovedByNamesCombination(NameFilter filter) {
        Page<DoctorEntity> entityPage = repository.findAllUnapprovedByNamesCombination(
                new DoctorNamesCombination(filter.getFirstName(), filter.getSecondName(), filter.getLastName()),
                PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("createdAt").descending())
        );
        return new PageResponse<>(
                this.toFullPrivateDto(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "doctors:approve",
            key = "#page.getPage() + ':' + #page.getSize()",
            condition = "#page.getPage() < 3 && #page.getSize() == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicDoctorResponseDto> findAllApproved(com.aidcompass.core.general.contracts.dto.PageRequest page) {
        Page<DoctorEntity> entityPage = repository.findAllByApprovedTrue(
                Pageable.ofSize(page.getSize()).withPage(page.getPage())
        );
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "doctors:spec",
            key = "#filter.getSpecialization() + ':' + #filter.getPage() + ':' + #filter.getSize()",
            condition = "#filter.getPage() == 0 && #filter.getSize() == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicDoctorResponseDto> findAllBySpecialization(DoctorSpecializationFilter filter) {
        DoctorSpecializationEntity entity = specializationService.findEntityBySpecialization(filter.getSpecialization());
        Page<DoctorEntity> entityPage = repository.findAllBySpecialization(
                entity,
                Pageable.ofSize(filter.getSize()).withPage(filter.getPage())
        );
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "doctors:name",
            key = "#filter.getFirstName() + ':' + #filter.getSecondName() + ':' + #filter.getLastName() + ':' + #filter.getPage() + ':' + #filter.getSize()",
            condition = "#filter.getPage() == 0 && #filter.getSize() == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicDoctorResponseDto> findAllByNamesCombination(NameFilter filter) {
        System.out.println(filter);
        DoctorNamesCombination specification = new DoctorNamesCombination(filter.getFirstName(), filter.getSecondName(), filter.getLastName());
        Page<DoctorEntity> entityPage = repository.findAllByNamesCombination(
                specification, Pageable.ofSize(filter.getSize()).withPage(filter.getPage())
        );
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "doctors:gender",
            key = "#gender + ':' + #page.getPage() + ':' + #page.getSize()",
            condition = "#page.getPage() < 3 && #page.getSize() == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicDoctorResponseDto> findAllByGender(Gender gender, com.aidcompass.core.general.contracts.dto.PageRequest page) {
        Page<DoctorEntity> entityPage = repository.findAllByGender(gender, Pageable.ofSize(page.getSize()).withPage(page.getPage()));
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "doctors:public", key = "#id"),
                    @CacheEvict(value = "doctors:public:full", key = "#id")
            }
    )
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Map<UUID, List<DoctorSpecialization>> loadSpecializations(Page<DoctorEntity> page) {
        List<UUID> ids = page.stream().map(DoctorEntity::getId).toList();
        return specializationService.findAllByDoctorIdIn(ids);
    }

    private List<PublicDoctorResponseDto> toPublicDtoList(List<DoctorEntity> entityList,
                                                          Map<UUID, List<DoctorSpecialization>> doctorSpecMap) {
        List<PublicDoctorResponseDto> responsList = new ArrayList<>();
        for (DoctorEntity entity : entityList) {
            responsList.add(mapper.toPublicDto(doctorSpecMap.get(entity.getId()), entity));
        }
        return responsList;
    }

    private List<FullPrivateDoctorResponseDto> toFullPrivateDto(List<DoctorEntity> entityList,
                                                                Map<UUID, List<DoctorSpecialization>> spacializationsMap) {
        List<FullPrivateDoctorResponseDto> dtoList = new ArrayList<>();
        for (DoctorEntity doctorEntity: entityList) {
            dtoList.add(fullMapper.toFullPrivateDto(spacializationsMap.get(doctorEntity.getId()), doctorEntity));
        }
        return dtoList;
    }
}
