package com.aidcompass.message;

import com.aidcompass.message.models.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailMessageService implements MessageService {

    private final JavaMailSender mailSender;


    @Override
    public void sendMessage(MessageDto messageDto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(messageDto.recipient());
        helper.setSubject(messageDto.subject());
        helper.setText(messageDto.text(), true);
        mailSender.send(mimeMessage);
    }

}
