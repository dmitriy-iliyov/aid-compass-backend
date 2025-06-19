package com.aidcompass.contracts;

import com.aidcompass.base_dto.AuthRequest;
import com.aidcompass.enums.Authority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface AuthService {
    void login(AuthRequest requestDto, HttpServletRequest request, HttpServletResponse response);

    void changeAuthorityById(UUID id, Authority authority, HttpServletResponse response);
}
