package aidcompass.api.confirmation_service;


import aidcompass.api.confirmation_service.models.EmailConfirmationEntity;
import aidcompass.api.message_service.models.MessageDto;
import aidcompass.api.message_service.EmailService;
import aidcompass.api.user.UserService;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class EmailConfirmationService implements ConfirmationService {

    private static final String URL = "https://localhost:8443/email/confirm?token=";
    private static final Duration tokenTtl = Duration.ofMinutes(60);

    private final EmailService emailService;
    private final UserService userService;
    private final EmailConfirmationRepository validationRepository;


    private EmailConfirmationEntity generateToken(String recipientMail){
        UUID token = UUID.randomUUID();
        Instant expiresAt = Instant.now().plus(tokenTtl);
        return new EmailConfirmationEntity(recipientMail, token, expiresAt);
    }

    @Transactional
    public void sendConfirmationMessage(String recipientMail) {

        EmailConfirmationEntity entity = generateToken(recipientMail);
        String encodeToken = Base64.getUrlEncoder().encodeToString(entity.getToken().toString().getBytes());

        MessageDto message = new MessageDto(recipientMail, "Email confirming.",
                URL + encodeToken);

        emailService.sendMessage(message);
        validationRepository.save(entity);
    }

    @Transactional
    public boolean validateConfirmationAnswer(String input) {

        String decodeInput = Strings.ascii(Base64.getUrlDecoder().decode(input.getBytes()));
        UUID inputToken = UUID.fromString(decodeInput);

        EmailConfirmationEntity entity = validationRepository.findByToken(inputToken)
                .orElseThrow(EntityNotFoundException::new);

        if (entity != null && entity.getExpiresAt() != null
                && Instant.now().isBefore(entity.getExpiresAt())
                && entity.getToken().compareTo(inputToken) == 0) {
            userService.confirmByEmail(entity.getEmail());
            validationRepository.deleteByToken(inputToken);
            return true;
        }
        return false;
    }
}
