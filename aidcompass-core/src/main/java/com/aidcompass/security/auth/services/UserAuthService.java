package com.aidcompass.security.auth.services;

import com.aidcompass.security.auth.dto.AuthRequest;
import com.aidcompass.security.domain.authority.models.Authority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface AuthService {
    void login(AuthRequest requestDto, HttpServletRequest request, HttpServletResponse response);

    void changeAuthorityById(UUID id, Authority authority, HttpServletResponse response);
}
