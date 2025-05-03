package com.aidcompass.user.repositories;

import com.aidcompass.user.models.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityByEmail(String email);
}
