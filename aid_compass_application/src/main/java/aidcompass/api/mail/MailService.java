package aidcompass.api.mail;

import aidcompass.api.mail.model.MailEntity;
import aidcompass.api.mail.model.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailRepository mailRepository;

    private final JavaMailSender mailSender;


    public void sendMessage(MessageDto messageDto){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(messageDto.recipient());
            helper.setSubject(messageDto.subject());
            helper.setText(messageDto.text(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email {}", e.getMessage());
        }
    }

    @Transactional
    public void sendConfirmingMessage(String recipient){
        String confirmingKey = MailUtils.generateConfirmKey();
        mailRepository.save(new MailEntity(recipient, confirmingKey));
        sendMessage(new MessageDto(recipient, "Confirming mail.", confirmingKey));
    }

    @Transactional
    public boolean validateConfirmingMessage(String mail, String currentKey){
        MailEntity mailEntity = mailRepository.findByMail(mail).orElseThrow(EntityNotFoundException::new);
        if (MailUtils.validateConfirmKey(currentKey, mailEntity.getKey())){
            mailRepository.delete(mailEntity);
            return true;
        }
        return false;
    }
}
