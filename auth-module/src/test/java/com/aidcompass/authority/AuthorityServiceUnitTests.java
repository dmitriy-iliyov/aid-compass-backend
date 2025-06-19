//package com.aidcompass.authority;
//
//import com.aidcompass.enums.Authority;
//import com.aidcompass.security.core.models.authority.AuthorityRepository;
//import com.aidcompass.security.core.models.authority.AuthorityServiceImpl;
//import com.aidcompass.security.core.models.authority.models.AuthorityEntity;
//import com.aidcompass.exceptions.not_found.AuthorityNotFoundException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthorityServiceUnitTests {
//
//    @Mock
//    AuthorityRepository authorityRepository;
//
//    @InjectMocks
//    AuthorityServiceImpl authorityService;
//
//
//    @Test
//    @DisplayName("UT: findByAuthority() should return AuthorityEntity when authority exists")
//    void findByAuthority_whenAuthorityExist() {
//        Authority authority = Authority.ROLE_USER;
//        AuthorityEntity authorityEntity = new AuthorityEntity(1L, authority);
//        doReturn(Optional.of(authorityEntity)).when(authorityRepository).findByAuthority(any(Authority.class));
//
//        assertEquals(authorityEntity, authorityService.findByAuthority(authority));
//
//        verify(authorityRepository, times(1)).findByAuthority(any(Authority.class));
//        verifyNoMoreInteractions(authorityRepository);
//    }
//
//    @Test
//    @DisplayName("UT: findByAuthority() should throw AuthorityNotFoundException when authority does not exist")
//    void findByAuthority_whenAuthorityNotExist_shouldThrowException() {
//        Authority authority = Authority.ROLE_USER;
//        doThrow(new AuthorityNotFoundException()).when(authorityRepository).findByAuthority(any(Authority.class));
//
//        assertThrows(AuthorityNotFoundException.class, () -> authorityService.findByAuthority(authority));
//
//        verify(authorityRepository, times(1)).findByAuthority(any(Authority.class));
//        verifyNoMoreInteractions(authorityRepository);
//    }
//
//    @Test
//    @DisplayName("UT: toAuthorityEntityList() should return list of AuthorityEntity when all authorities exist")
//    void toAuthorityEntityList_whenAllAuthoritiesExist_shouldReturnEntities() {
//        List<Authority> authorities = List.of(Authority.ROLE_USER, Authority.ROLE_ADMIN);
//        AuthorityEntity userEntity = new AuthorityEntity(1L, Authority.ROLE_USER);
//        AuthorityEntity adminEntity = new AuthorityEntity(2L, Authority.ROLE_ADMIN);
//
//        when(authorityRepository.findByAuthority(Authority.ROLE_USER)).thenReturn(Optional.of(userEntity));
//        when(authorityRepository.findByAuthority(Authority.ROLE_ADMIN)).thenReturn(Optional.of(adminEntity));
//
//        List<AuthorityEntity> result = authorityService.toAuthorityEntityList(authorities);
//
//        assertEquals(2, result.size());
//        assertTrue(result.contains(userEntity));
//        assertTrue(result.contains(adminEntity));
//
//        verify(authorityRepository, times(1)).findByAuthority(Authority.ROLE_USER);
//        verify(authorityRepository, times(1)).findByAuthority(Authority.ROLE_ADMIN);
//        verifyNoMoreInteractions(authorityRepository);
//    }
//
//    @Test
//    @DisplayName("UT: toAuthorityEntityList() should throw AuthorityNotFoundException when any authority not found")
//    void toAuthorityEntityList_whenAuthorityNotFound_shouldThrowException() {
//        List<Authority> authorities = List.of(Authority.ROLE_USER, Authority.ROLE_ADMIN);
//
//        when(authorityRepository.findByAuthority(Authority.ROLE_USER))
//                .thenReturn(Optional.of(new AuthorityEntity(1L, Authority.ROLE_USER)));
//        when(authorityRepository.findByAuthority(Authority.ROLE_ADMIN))
//                .thenReturn(Optional.empty());
//
//        assertThrows(AuthorityNotFoundException.class,
//                () -> authorityService.toAuthorityEntityList(authorities));
//
//        verify(authorityRepository, times(1)).findByAuthority(Authority.ROLE_USER);
//        verify(authorityRepository, times(1)).findByAuthority(Authority.ROLE_ADMIN);
//        verifyNoMoreInteractions(authorityRepository);
//    }
//
//}
