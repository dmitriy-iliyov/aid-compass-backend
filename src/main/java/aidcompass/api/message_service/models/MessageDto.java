package aidcompass.api.message_service.models;


public record MessageDto (
        String recipientMail,
        String subject,
        String text
){}
