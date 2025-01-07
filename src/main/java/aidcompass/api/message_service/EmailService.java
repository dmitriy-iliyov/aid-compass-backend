package aidcompass.api.message_service;

import aidcompass.api.message_service.models.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService implements MessageService {

    private final JavaMailSender mailSender;


    public void sendMessage(MessageDto messageDto){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(messageDto.recipientMail());
            helper.setSubject(messageDto.subject());
            helper.setText(messageDto.text(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email {}", e.getMessage());
        }
    }

}
