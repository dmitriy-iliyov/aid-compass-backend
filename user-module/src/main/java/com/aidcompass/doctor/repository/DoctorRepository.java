package com.aidcompass.doctor.repository;


import com.aidcompass.enums.gender.Gender;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID>,
        JpaSpecificationExecutor<DoctorEntity>,
        DoctorAdditionalRepository {

    @Modifying
    @Query("UPDATE DoctorEntity d SET d.isApproved = true WHERE d.id = :id")
    void approveById(@Param("id") UUID id);

    @Query("SELECT d FROM DoctorEntity d JOIN FETCH d.profileStatusEntity WHERE d.id = :id")
    Optional<DoctorEntity> findWithProfileStatusById(@Param("id") UUID id);

    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                WHERE d.id = :id 
                AND d.isApproved = true
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
    """)
    Optional<DoctorEntity> findWithSpecsById(@Param("id") UUID id);


    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.profileStatusEntity 
                WHERE d.id = :id
    """)
    Optional<DoctorEntity> findWithSpecsAndProfileStatusById(@Param("id") UUID id);


    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.detailEntity 
                WHERE d.id = :id 
                AND d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
    """)
    Optional<DoctorEntity> findWithSpecsAndDetailById(@Param("id") UUID id);


    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.profileStatusEntity 
                JOIN FETCH d.detailEntity 
                WHERE d.id = :id
    """)
    Optional<DoctorEntity> findWithAllById(@Param("id") UUID id);


    @Query(
           value = """
               SELECT d FROM DoctorEntity d 
               WHERE d.isApproved = true 
               AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
           """,
            countQuery = """
               SELECT COUNT(d) FROM DoctorEntity d
               WHERE d.isApproved = true
               AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
            """
    )
    Page<DoctorEntity> findAllByApprovedTrue(Pageable pageable);


    @Query(
            value = """
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.profileStatusEntity
                JOIN FETCH d.detailEntity 
                WHERE d.isApproved = false 
            """,
            countQuery = """
                SELECT COUNT(d) FROM DoctorEntity d
                WHERE d.isApproved = false
            """
    )
    Page<DoctorEntity> findAllByApprovedFalse(Pageable pageable);


    @Query(
            value = """
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.profileStatusEntity
                WHERE :specialization MEMBER OF d.specializations 
                AND d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
            """,
            countQuery = """
                SELECT COUNT(d) FROM DoctorEntity d
                WHERE :specialization MEMBER OF d.specializations 
                AND d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
            """
    )
    Page<DoctorEntity> findAllBySpecialization(@Param("specialization") DoctorSpecializationEntity specialization,
                                               Pageable pageable);

    @Query(
            value = """
                SELECT d FROM DoctorEntity d
                WHERE d.gender = :gender
                AND d.isApproved = true
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
            """,
            countQuery = """
                SELECT COUNT(d) FROM DoctorEntity d
                WHERE d.gender = :gender
                AND d.isApproved = true
                AND d.profileStatusEntity.profileStatus = com.aidcompass.enums.ProfileStatus.COMPLETE
            """
    )
    Page<DoctorEntity> findAllByGender(@Param("gender") Gender gender, Pageable pageable);

    @Modifying
    @Query("""
                UPDATE DoctorEntity d
                SET d.profileProgress = d.profileProgress + :increment
                WHERE d.id = :id
    """)
    void updateProfileProgress(@Param("id") UUID id, @Param("increment") int i);

    @Modifying
    @Query("""
                UPDATE DoctorEntity d
                SET d.profileProgress = d.profileProgress + :increment,
                    d.profileStatusEntity = :profileStatusEntity
                WHERE d.id = :id
    """)
    void updateProgressAndProfileStatus(@Param("id") UUID id, @Param("increment") int increment,
                                        @Param("profileStatusEntity") ProfileStatusEntity profileStatusEntity);

    long countByIsApproved(boolean approved);
}