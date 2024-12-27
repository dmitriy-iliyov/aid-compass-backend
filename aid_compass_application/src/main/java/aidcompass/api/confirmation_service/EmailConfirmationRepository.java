package aidcompass.api.confirmation_service;

import aidcompass.api.confirmation_service.models.EmailConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationEntity, Long> {

    Optional<EmailConfirmationEntity> findByToken(UUID token);

    void deleteByToken(UUID token);

}
