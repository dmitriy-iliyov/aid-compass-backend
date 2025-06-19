package com.aidcompass.user.repositories;

import com.aidcompass.enums.Authority;
import com.aidcompass.user.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityById(UUID id);

    @Query("""
               SELECT COUNT(u) > 0 FROM UserEntity u
               WHERE u.id = :id
               AND  :authority MEMBER OF u.authorities
    """)
    boolean existsByIdAndAuthority(@Param("id") UUID id, @Param("authority") Authority authority);
}
