package com.aidcompass.clients.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@RequiredArgsConstructor
public class RestClientContactService implements ContactService {

    private final RestClient restClient;


    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public void confirmContact(String email) {

    }

    @Override
    public String createContact(UUID id, String email) {
        return "";
    }

    @Override
    public void updateContact(String email) {

    }
}
