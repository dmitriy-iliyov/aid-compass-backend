package com.example;

import com.example.clicker.avatar.model.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID> {
}
