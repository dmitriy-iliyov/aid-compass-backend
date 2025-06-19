package com.aidcompass.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    ROLE_ANONYMOUS,
    ROLE_UNCONFIRMED_USER,
    ROLE_USER,
    ROLE_CUSTOMER,
    ROLE_DOCTOR,
    ROLE_JURIST,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
