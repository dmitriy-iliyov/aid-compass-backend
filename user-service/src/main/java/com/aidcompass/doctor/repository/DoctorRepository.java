package com.aidcompass.doctor.repository;


import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
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


    /**
     * Finds a doctor by id, fetching specializations.
     * Filters by isApproved = true and profileStatus = COMPLETE.
     */
    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                WHERE d.id = :id 
                AND d.isApproved = true
                AND d.profileStatusEntity.profileStatus = com.aidcompass.profile_status.models.ProfileStatus.COMPLETE
    """)
    Optional<DoctorEntity> findWithSpecsById(@Param("id") UUID id);


    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.profileStatusEntity 
                WHERE d.id = :id
    """)
    Optional<DoctorEntity> findWithSpecsAndProfileStatusById(@Param("id") UUID id);


    /**
     * Finds a doctor by id, fetching specializations and details.
     * Filters by isApproved = true and profileStatus = COMPLETE.
     */
    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.detailEntity 
                WHERE d.id = :id 
                AND d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.profile_status.models.ProfileStatus.COMPLETE
    """)
    Optional<DoctorEntity> findWithSpecsAndDetailById(@Param("id") UUID id);


    /**
     * Finds a doctor by id, fetching specializations, profile profileStatus, and details.
     */
    @Query("""
                SELECT d FROM DoctorEntity d 
                JOIN FETCH d.specializations 
                JOIN FETCH d.profileStatusEntity 
                JOIN FETCH d.detailEntity 
                WHERE d.id = :id
    """)
    Optional<DoctorEntity> findFullById(@Param("id") UUID id);


    /**
     * Returns a slice of doctors where isApproved = true and profileStatus = COMPLETE.
     */
    @EntityGraph(attributePaths = {"specializations"})
    @Query("""
                SELECT d FROM DoctorEntity d 
                WHERE d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.profile_status.models.ProfileStatus.COMPLETE
    """)
    Slice<DoctorEntity> findAllByApprovedTrue(Pageable pageable);


    @EntityGraph(attributePaths = {"specializations", "profileStatus"})
    @Query("""
                SELECT d FROM DoctorEntity d 
                WHERE d.isApproved = false 
    """)
    Slice<DoctorEntity> findAllByApprovedFalse(Pageable pageable);


    /**
     * Returns a slice of doctors with the specified specialization,
     * where isApproved = true and profileStatus = COMPLETE.
     * Fetches specializations and profile profileStatus.
     */
    @EntityGraph(attributePaths = {"specializations", "profileStatus"})
    @Query("""
                SELECT d FROM DoctorEntity d 
                WHERE :specialization MEMBER OF d.specializations 
                AND d.isApproved = true 
                AND d.profileStatusEntity.profileStatus = com.aidcompass.profile_status.models.ProfileStatus.COMPLETE
    """)
    Slice<DoctorEntity> findAllBySpecialization(@Param("specialization") DoctorSpecializationEntity specialization,
                                                Pageable pageable);


    @Query("SELECT d.profileStatusEntity FROM DoctorEntity d WHERE d.id = :id")
    Optional<ProfileStatusEntity> findProfileStatusByDoctorId(@Param("id") UUID id);
}