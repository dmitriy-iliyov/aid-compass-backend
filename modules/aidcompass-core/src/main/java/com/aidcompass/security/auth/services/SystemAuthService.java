package com.aidcompass.security.auth.services;

import com.aidcompass.security.auth.dto.ServiceAuthRequest;

import java.util.Map;

public interface SystemAuthService {
    Map<String, String> login(ServiceAuthRequest requestDto);

    Map<String, String> generateToken(String serviceName, Integer daysTtl);
}
