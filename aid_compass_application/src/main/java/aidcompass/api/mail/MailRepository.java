package aidcompass.api.mail;

import aidcompass.api.mail.model.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {
    Optional<MailEntity> findByMail(String mail);
}

