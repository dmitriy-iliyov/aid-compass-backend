//package com.aidcompass.contact.validation.count;
//
//import com.aidcompass.contact.services.SystemContactService;
//import com.aidcompass.contact.validation.validators.impl.CountValidatorImpl;
//import com.aidcompass.enums.ContactType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.UUID;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CountValidatorImplUnitTests {
//
//    @Mock
//    SystemContactService systemService;
//
//    @InjectMocks
//    CountValidatorImpl validator;
//
//
//    @Test
//    @DisplayName("UT hasSpaceForEmails() when has spase for new email should return true")
//    void hasSpaceForEmails_whenHasSpase_shouldReturnTrue() {
//        UUID ownerId = UUID.randomUUID();
//        long count = 1;
//
//        doReturn(1L).when(systemService).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//
//        boolean response = validator.hasSpaceForEmails(ownerId, count);
//
//        assertTrue(response);
//
//        verify(systemService, times(1)).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//        verifyNoMoreInteractions(systemService);
//    }
//
//    @Test
//    @DisplayName("UT hasSpaceForEmails() when has not spase for new email should return false")
//    void hasSpaceForEmails_whenHasNotSpase_shouldReturnTrue() {
//        UUID ownerId = UUID.randomUUID();
//        long count = 4;
//
//        doReturn(1L).when(systemService).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//
//        boolean response = validator.hasSpaceForEmails(ownerId, count);
//
//        assertFalse(response);
//
//        verify(systemService, times(1)).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//        verifyNoMoreInteractions(systemService);
//    }
//
//    @Test
//    @DisplayName("UT hasSpaceForEmails() when has last spase for email should return true")
//    void hasSpaceForEmails_whenTheLastSpase_shouldReturnTrue() {
//        UUID ownerId = UUID.randomUUID();
//        long count = 2;
//
//        doReturn(1L).when(systemService).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//
//        boolean response = validator.hasSpaceForEmails(ownerId, count);
//
//        assertTrue(response);
//
//        verify(systemService, times(1)).countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
//        verifyNoMoreInteractions(systemService);
//    }
//}
