package com.aidcompass;

import com.aidcompass.model.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID> {

    Optional<AvatarEntity> findByUserId(UUID userId);

    List<AvatarEntity> findAllByUserIdIn(List<UUID> userIdList);
}
