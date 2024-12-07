package aidcompass.api.mail;
import com.example.clickerapi.services.mail.models.EmailValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface EmailValidationRepository extends JpaRepository<EmailValidationEntity, Long> {

    Optional<EmailValidationEntity> findByToken(UUID token);

    void deleteByToken(UUID token);

}
