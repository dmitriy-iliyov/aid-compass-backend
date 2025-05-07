package com.aidcompass.clients.contacts;

import java.util.UUID;

public interface ContactService {

    boolean isEmailExist(String email);

    void confirmContact(String email);

    String createContact(UUID id, String email);
}
