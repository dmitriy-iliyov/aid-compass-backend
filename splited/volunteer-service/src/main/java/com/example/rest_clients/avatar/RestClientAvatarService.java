package com.example.rest_clients.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class RestClientAvatarService implements AvatarService {

    private final RestClient restClient;



}
