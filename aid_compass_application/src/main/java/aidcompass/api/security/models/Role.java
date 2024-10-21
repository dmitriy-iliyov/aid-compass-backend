//package aidcompass.api.security.models;
//
//import org.springframework.security.core.GrantedAuthority;
//
//public enum Role implements GrantedAuthority {
//
//    ROLE_USER, ROLE_DOCTOR, ROLE_LAWYER, ROLE_VOLUNTEER, ROLE_ADMIN;
//
//    @Override
//    public String getAuthority() {
//        return name();
//    }
//
//}

package aidcompass.api.security.models;

public enum Role{

    ROLE_USER, ROLE_DOCTOR, ROLE_LAWYER, ROLE_VOLUNTEER, ROLE_ADMIN;

}
