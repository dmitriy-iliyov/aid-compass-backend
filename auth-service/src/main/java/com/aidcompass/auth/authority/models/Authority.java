package com.aidcompass.auth.authority.models;

//import org.springframework.security.core.GrantedAuthority;
//
//public enum Authority implements GrantedAuthority {
//
//    ROLE_UNCONFIRMED_USER, ROLE_USER, ROLE_ADMIN;
//
//    @Override
//    public String getAuthority() {
//        return name();
//    }
//}


public enum Authority {

    ROLE_UNCONFIRMED_USER, ROLE_USER, ROLE_ADMIN;

}
