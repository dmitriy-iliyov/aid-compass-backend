//package aidcompass.api.security.models.security_user.models;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//
//
//public class UserDetails extends User {
//
//    private final Logger logger = LoggerFactory.getLogger(SecurityUserDetails.class);
//
//    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//        logger.info("DefaultUserDetails constructor involved");
//    }
//
//    public String getRole(){
//        return getAuthorities().stream()
//                .findFirst()
//                .map(GrantedAuthority::getAuthority)
//                .orElse(null);
//    }
//
//}
