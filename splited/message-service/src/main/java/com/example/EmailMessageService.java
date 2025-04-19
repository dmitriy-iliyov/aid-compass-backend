package com.example;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailMessageService implements MessageService {

    private final JavaMailSender mailSender;

    @Async
    @Override
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

}
