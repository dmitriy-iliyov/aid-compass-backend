package com.aidcompass.exceptions;

import lombok.Getter;

@Getter
public class ShouldRedirect extends RuntimeException {

    private final String targetUrl;


    public ShouldRedirect(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
