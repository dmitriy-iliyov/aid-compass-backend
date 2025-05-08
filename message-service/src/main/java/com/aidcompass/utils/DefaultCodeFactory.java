package com.aidcompass.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class DefaultCodeFactory implements CodeFactory {

    private final SecureRandom random = new SecureRandom();


    @Override
    public String generate() {
        int code = random.nextInt(100_000, 1_000_000);
        return String.valueOf(code);
    }
}
