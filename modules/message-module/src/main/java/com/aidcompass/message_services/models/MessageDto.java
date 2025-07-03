package com.aidcompass.message_services.models;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}
