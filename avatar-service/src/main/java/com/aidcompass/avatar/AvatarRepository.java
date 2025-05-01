package com.aidcompass.avatar;

import com.aidcompass.avatar.model.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID> {

    Optional<AvatarEntity> findByUserId(UUID userId);
}
