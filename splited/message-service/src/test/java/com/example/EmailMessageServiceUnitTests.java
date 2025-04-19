package com.example;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.AsyncResult;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmailMessageServiceUnitTests {

    private JavaMailSender javaMailSender;
    private EmailMessageService emailMessageService;

    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        emailMessageService = new EmailMessageService(javaMailSender);
    }

    @Test
    void sendMessage_shouldCreateProperMimeMessage() {
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MessageDto messageDto = new MessageDto(
                "test@example.com",
                "Test Subject",
                "<h1>Hello!</h1>"
        );

        emailMessageService.sendMessage(messageDto);

        verify(javaMailSender, times(1)).createMimeMessage();
        verify(javaMailSender, times(1)).send(mimeMessage);
        verifyNoMoreInteractions(javaMailSender);
    }

    @Test
    void sendMessage_shouldLogErrorOnMessagingException() {
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MessageDto messageDto = new MessageDto(
                "fail@example.com",
                "Fail Subject",
                "This will fail"
        );

        doThrow(new RuntimeException("Simulated failure")).when(javaMailSender).send(any(MimeMessage.class));

        assertThrows(RuntimeException.class, () -> emailMessageService.sendMessage(messageDto));

        verify(javaMailSender, times(1)).createMimeMessage();
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        verifyNoMoreInteractions(javaMailSender);
        // Тут логгирование проверить можно с помощью библиотеки типа LogCaptor
    }

    @Test
    void sendMessage_shouldBeAnnotatedWithAsync() throws NoSuchMethodException {
        Method method = EmailMessageService.class.getMethod("sendMessage", MessageDto.class);
        assertThat(method.isAnnotationPresent(org.springframework.scheduling.annotation.Async.class))
                .isTrue();
    }
}
