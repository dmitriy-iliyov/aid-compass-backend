package aidcompass.api.mail.model;

public record MessageDto(
        String recipient,
        String subject,
        String text)
{ }
