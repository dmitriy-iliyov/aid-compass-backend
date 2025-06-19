//package com.aidcompass.authority;
//
//import com.aidcompass.enums.Authority;
//import com.aidcompass.security.core.models.authority.AuthorityRepository;
//import com.aidcompass.security.core.models.authority.AuthorityService;
//import com.aidcompass.security.core.models.authority.models.AuthorityEntity;
//import com.aidcompass.exceptions.not_found.AuthorityNotFoundException;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("test")
//@SpringBootTest
//public class AuthorityServiceIntegrationTests {
//
//    @Autowired
//    AuthorityRepository authorityRepository;
//
//    @Autowired
//    AuthorityService authorityService;
//
//    final List<AuthorityEntity> authorityEntityList;
//
//    public AuthorityServiceIntegrationTests() {
//        this.authorityEntityList = new ArrayList<>();
//    }
//
//    @BeforeAll
//    void setUp() {
//        authorityEntityList.add(authorityRepository.save(new AuthorityEntity(null, Authority.ROLE_USER)));
//        authorityEntityList.add(authorityRepository.save(new AuthorityEntity(null, Authority.ROLE_UNCONFIRMED_USER)));
//    }
//
//    @Test
//    @DisplayName("IT: findByAuthority() should return AuthorityEntity when authority exists")
//    void findByAuthority_whenAuthorityExist() {
//        AuthorityEntity foundEntity = authorityService.findByAuthority(Authority.ROLE_USER);
//        assertEquals(foundEntity.getAuthority(), Authority.ROLE_USER);
//    }
//
//    @Test
//    @DisplayName("IT: findByAuthority() should throw AuthorityNotFoundException")
//    void findByAuthority_whenAuthorityNotExist_shouldThrowAuthorityNotFoundException() {
//        assertThrows(AuthorityNotFoundException.class, () -> authorityService.findByAuthority(Authority.ROLE_ADMIN));
//    }
//
//    @Test
//    @DisplayName("IT: toAuthorityEntityList() should return list of AuthorityEntity")
//    void toAuthorityEntityList_whenAuthorityExist() {
//        assertEquals(authorityEntityList, authorityService.toAuthorityEntityList(
//                List.of(Authority.ROLE_USER, Authority.ROLE_UNCONFIRMED_USER))
//        );
//    }
//
//    @Test
//    @DisplayName("IT: toAuthorityEntityList() should throw AuthorityNotFoundException")
//    void toAuthorityEntityList_whenAuthorityNotExist_shouldThrowAuthorityNotFoundException() {
//        assertThrows(AuthorityNotFoundException.class, () -> authorityService.toAuthorityEntityList(
//                List.of(Authority.ROLE_USER, Authority.ROLE_ADMIN, Authority.ROLE_UNCONFIRMED_USER))
//        );
//    }
//
//    @Test
//    @DisplayName("IT: toAuthorityEntityList() should return empty list when passed empty list")
//    void toAuthorityEntityList_whenAuthorityExist_shouldReturnEmptyListWhenPassedEmptyList() {
//        assertEquals(List.of(), authorityService.toAuthorityEntityList(
//                List.of())
//        );
//    }
//}
