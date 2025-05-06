package com.aidcompass.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class RestClientContactService implements ContactService {

    private final RestClient restClient;


    @Override
    public void confirmContactById(Long contactId) {

    }
}
