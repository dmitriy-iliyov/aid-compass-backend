package com.aidcompass.customer;

import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @EntityGraph(attributePaths = {"profileStatusEntity"})
    @NonNull
    Optional<CustomerEntity> findById(@NonNull UUID id);

    @Query("""
        SELECT 
            CASE WHEN c.profileStatusEntity.profileStatus = com.aidcompass.profile_status.models.ProfileStatus.COMPLETE 
            THEN true ELSE false END
        FROM CustomerEntity c
        WHERE c.id = :id
    """)
    boolean isCompleted(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"profileStatusEntity"})
    Optional<CustomerEntity> findWithProfileStatusById(UUID id);

    @Modifying
    @Query("""
            UPDATE CustomerEntity c
            SET c.profileProgress = c.profileProgress + :profileProgressStep,
                c.profileStatusEntity = :profileStatusEntity
            WHERE c.id = :id
    """)
    void updateProgressAndProfileStatus(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep,
                                        @Param("profileStatusEntity") ProfileStatusEntity profileStatusEntity);

    @Modifying
    @Query("""
            UPDATE CustomerEntity c
            SET c.profileProgress = c.profileProgress + :profileProgressStep
            WHERE c.id = :id
    """)
    void updateProfileProgress(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep);
}