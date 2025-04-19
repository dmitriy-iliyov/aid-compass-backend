package com.example;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}
