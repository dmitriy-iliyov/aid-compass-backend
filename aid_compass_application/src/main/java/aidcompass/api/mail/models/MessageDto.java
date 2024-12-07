package aidcompass.api.mail.models;


public record MessageDto (
        String recipientMail,
        String subject,
        String text
){}
