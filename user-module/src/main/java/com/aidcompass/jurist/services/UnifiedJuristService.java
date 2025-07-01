package com.aidcompass.jurist.services;


import com.aidcompass.PageResponse;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.enums.gender.Gender;
import com.aidcompass.system.enums.ServiceType;
import com.aidcompass.general.exceptions.jurist.FullJuristNotFoundException;
import com.aidcompass.general.exceptions.jurist.JuristNotFoundByIdException;
import com.aidcompass.jurist.mapper.FullJuristMapper;
import com.aidcompass.jurist.mapper.JuristMapper;
import com.aidcompass.jurist.models.JuristEntity;
import com.aidcompass.jurist.dto.FullPrivateJuristResponseDto;
import com.aidcompass.jurist.dto.FullPublicJuristResponseDto;
import com.aidcompass.jurist.models.JuristDto;
import com.aidcompass.jurist.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.repository.JuristRepository;
import com.aidcompass.jurist.specialization.JuristSpecializationService;
import com.aidcompass.jurist.specialization.JuristTypeService;
import com.aidcompass.jurist.specialization.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristSpecializationEntity;
import com.aidcompass.jurist.type.JuristType;
import com.aidcompass.jurist.specialization.models.JuristTypeEntity;
import com.aidcompass.profile_status.ProfileConfig;
import com.aidcompass.profile_status.ProfileStatusService;
import com.aidcompass.profile_status.ProfileStatusUpdateService;
import com.aidcompass.enums.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.aidcompass.jurist.repository.JuristSpecifications.*;


@Service
@RequiredArgsConstructor
public class UnifiedJuristService implements JuristService, ProfileStatusUpdateService {

    private final ServiceType type = ServiceType.JURIST_SERVICE;
    private final JuristRepository repository;
    private final JuristMapper mapper;
    private final FullJuristMapper fullMapper;
    private final ProfileStatusService profileStatusService;
    private final JuristSpecializationService specializationService;
    private final JuristTypeService typeService;
    private final CacheManager cache;


    @Override
    public ServiceType getType() {
        return type;
    }

    @Transactional
    @Override
    public PrivateJuristResponseDto save(UUID id, DetailEntity detail, JuristDto dto) {
        JuristTypeEntity typeEntity = typeService.findEntityByType(JuristType.toEnum(dto.getType()));
        List<JuristSpecializationEntity> specializations =
                specializationService.findEntityListBySpecializationList(dto.getSpecializations());
        ProfileStatusEntity profileStatus = profileStatusService.findByStatus(ProfileStatus.INCOMPLETE);

        JuristEntity jurist = mapper.toEntity(id, dto);
        jurist.setTypeEntity(typeEntity);
        jurist.setSpecializations(specializations);
        jurist.setDetailEntity(detail);
        jurist.setProfileProgress(ProfileConfig.PROFILE_PROGRESS_STEP);
        jurist.setProfileStatusEntity(profileStatus);

        return mapper.toPrivateDto(repository.save(jurist));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "jurists:public", key = "#id",
                            condition = "#result == T(com.aidcompass.profile_status.ProfileConfig).MAX_PROFILE_PROGRESS"),
                    @CacheEvict(value = "jurists:public:full", key = "#id",
                            condition = "#result == T(com.aidcompass.profile_status.ProfileConfig).MAX_PROFILE_PROGRESS")
            }
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateProfileStatus(UUID id) {
        JuristEntity entity = repository.findWithProfileStatusById(id).orElseThrow(JuristNotFoundByIdException::new);
        int newProgress = entity.getProfileProgress() + ProfileConfig.PROFILE_PROGRESS_STEP;
        if (newProgress == ProfileConfig.MAX_PROFILE_PROGRESS) {
            ProfileStatusEntity profileStatus = profileStatusService.findByStatus(ProfileStatus.COMPLETE);
            repository.updateProfileProgressAndStatus(id, ProfileConfig.PROFILE_PROGRESS_STEP,profileStatus);
        } else if (newProgress < ProfileConfig.MAX_PROFILE_PROGRESS) {
            repository.updateProfileProgress(id, ProfileConfig.PROFILE_PROGRESS_STEP);
        }
        return newProgress;
    }

    @CacheEvict(value = "jurists:public:full", key = "#id")
    @Transactional
    @Override
    public PrivateJuristResponseDto update(UUID id, JuristDto dto) {
        //when spec change approved = false
        JuristEntity entity = repository.findWithTypeAndSpecsAndProfileStatusById(id).orElseThrow(JuristNotFoundByIdException::new);
        mapper.updateEntityFromUpdateDto(dto, entity);

        if (!dto.getType().equals(entity.getTypeEntity().getType().getTranslate())) {
            JuristTypeEntity typeEntity = typeService.findEntityByType(JuristType.toEnum(dto.getType()));
            entity.setTypeEntity(typeEntity);
        }

        List<JuristSpecializationEntity> specializations =
                specializationService.findEntityListBySpecializationList(dto.getSpecializations());

        entity.setSpecializations(specializations);
        entity = repository.save(entity);

        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            PublicJuristResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("jurists:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Transactional
    @Override
    public void approve(UUID id) {
        repository.approveById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateJuristResponseDto findPrivateById(UUID id) {
        JuristEntity entity = repository.findWithTypeAndSpecsAndProfileStatusById(id).orElseThrow(
                JuristNotFoundByIdException::new
        );
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            PublicJuristResponseDto publicDto = mapper.toPublicDto(entity);
            Objects.requireNonNull(cache.getCache("jurists:public")).put(publicDto.id(), publicDto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Cacheable(value = "jurists:public", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public PublicJuristResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findWithTypeAndSpecsById(id).orElseThrow(JuristNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public FullPrivateJuristResponseDto findFullPrivateById(UUID id) {
        JuristEntity entity = repository.findWithAllById(id).orElseThrow(FullJuristNotFoundException::new);
        if (entity.getProfileStatusEntity().getProfileStatus() == ProfileStatus.COMPLETE && entity.isApproved()) {
            FullPublicJuristResponseDto publicDto = fullMapper.toFullPublicDto(entity);
            Objects.requireNonNull(cache.getCache("jurists:public:full")).put(id, publicDto);
        }
        return fullMapper.toFullPrivateDto(entity);
    }

    @Cacheable(value = "jurists:public:full", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public FullPublicJuristResponseDto findFullPublicById(UUID id) {
        JuristEntity entity = repository.findWithTypeAndSpecsAndDetailById(id).orElseThrow(
                FullJuristNotFoundException::new
        );
        return fullMapper.toFullPublicDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByIsApproved(boolean approved) {
        return repository.countByIsApproved(approved);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullPrivateJuristResponseDto> findAllUnapproved(int page, int size) {
        Page<JuristEntity> entityPage = repository.findAllByApprovedFalse(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
        return new PageResponse<>(
                this.toFullPrivateDto(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullPrivateJuristResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                          String lastName, int page, int size) {
        Specification<JuristEntity> specification = Specification
                .where(hasFirstName(firstName))
                .and(hasSecondName(secondName))
                .and(hasLastName(lastName))
                .and(hasNotApproval());

        Page<JuristEntity> entityPage = repository.findAll(
                specification,
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
        return new PageResponse<>(
                this.toFullPrivateDto(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "jurists:approve",
            key = "#page + ':' + #size",
            condition = "#page < 3 && #size == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicJuristResponseDto> findAllApproved(int page, int size) {
        Page<JuristEntity> entityPage = repository.findAllByApprovedTrue(
                Pageable.ofSize(size).withPage(page)
        );
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "jurists:spec",
            key = "((#type == null) ? 'null' : #type) + ':' +" +
                  " ((#specialization == null) ? 'null' : #specialization) + ':' + #page + ':' + #size",
            condition = "#page == 0 && #size == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicJuristResponseDto> findAllByTypeAndSpecialization(String type, String specialization,
                                                                                int page, int size) {
        JuristTypeEntity typeEntity = null;
        JuristSpecializationEntity specializationEntity = null;
        if (type != null) {
            typeEntity = typeService.findEntityByType(JuristType.toEnum(type));
        }
        if (specialization != null) {
            specializationEntity = specializationService.findEntityBySpecialization(JuristSpecialization.toEnum(specialization));
        }

        Specification<JuristEntity> specification = Specification
                .where(hasType(typeEntity))
                .and(hasSpecialization(specializationEntity))
                .and(hasApproval())
                .and(hasCompletedProfile());

        Page<JuristEntity> entityPage = repository.findAll(specification, Pageable.ofSize(size).withPage(page));

        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "jurists:name",
            key = "#type + ':' + #firstName + ':' + #secondName + ':' + #lastName + ':' + #page + ':' + #size",
            condition = "#page == 0 && #size == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicJuristResponseDto> findAllByTypeAndNamesCombination(String type,
                                                                                  String firstName, String secondName,
                                                                                  String lastName, int page, int size) {
        JuristTypeEntity typeEntity = null;
        if (type != null) {
            typeEntity = typeService.findEntityByType(JuristType.toEnum(type));
        }

        Specification<JuristEntity> specification = Specification
                .where(hasType(typeEntity))
                .and(hasFirstName(firstName))
                .and(hasSecondName(secondName))
                .and(hasLastName(lastName))
                .and(hasApproval())
                .and(hasCompletedProfile());

        Page<JuristEntity> entityPage = repository.findAll(specification, Pageable.ofSize(size).withPage(page));

        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    //cache
//    @Transactional(readOnly = true)
//    @Override
    public PageResponse<PublicJuristResponseDto> findAllByFilter(String type, String specialization,
                                                                 String firstName, String secondName,
                                                                 String lastName, int page, int size) {

        JuristTypeEntity typeEntity = null;
        JuristSpecializationEntity specializationEntity = null;

        if (type != null) {
            typeEntity = typeService.findEntityByType(JuristType.toEnum(type));
        }

        if (specialization != null) {
            specializationEntity = specializationService.findEntityBySpecialization(
                    JuristSpecialization.toEnum(specialization));
        }

        Specification<JuristEntity> specification = Specification
                .where(hasType(typeEntity))
                .and(hasSpecialization(specializationEntity))
                .and(hasFirstName(firstName))
                .and(hasSecondName(secondName))
                .and(hasLastName(lastName))
                .and(hasApproval())
                .and(hasCompletedProfile());

        Page<JuristEntity> entityPage = repository.findAll(specification, Pageable.ofSize(size).withPage(page));

        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = "jurists:gender",
            key = "#gender + ':' + #page + ':' + #size",
            condition = "#page < 3 && #size == 10"
    )
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PublicJuristResponseDto> findAllByGender(Gender gender, int page, int size) {
        Page<JuristEntity> entityPage = repository.findAllByGender(gender, Pageable.ofSize(size).withPage(page));
        return new PageResponse<>(
                this.toPublicDtoList(entityPage.getContent(), this.loadSpecializations(entityPage)),
                entityPage.getTotalPages()
        );
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "jurists:public", key = "#id"),
                    @CacheEvict(value = "jurists:public:full", key = "#id")
            }
    )
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Map<UUID, List<JuristSpecialization>> loadSpecializations(Page<JuristEntity> entityPage) {
        List<UUID> ids = entityPage.stream().map(JuristEntity::getId).toList();
        return specializationService.findAllByJuristIds(ids);
    }

    private List<PublicJuristResponseDto> toPublicDtoList(List<JuristEntity> juristEntityList,
                                                          Map<UUID, List<JuristSpecialization>> juristSpecMap) {
        List<PublicJuristResponseDto> publicDtoList = new ArrayList<>();
        for (JuristEntity entity : juristEntityList) {
            publicDtoList.add(mapper.toPublicDto(juristSpecMap.get(entity.getId()), entity));
        }
        return publicDtoList;
    }

    private List<FullPrivateJuristResponseDto> toFullPrivateDto(List<JuristEntity> juristEntityList,
                                                                Map<UUID, List<JuristSpecialization>> spacializationsMap) {
        List<FullPrivateJuristResponseDto> dtoList = new ArrayList<>();
        for (JuristEntity juristEntity: juristEntityList) {
            dtoList.add(fullMapper.toFullPrivateDto(spacializationsMap.get(juristEntity.getId()), juristEntity));
        }
        return dtoList;
    }
}
