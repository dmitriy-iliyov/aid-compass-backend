package com.aidcompass.message.message.models;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}
