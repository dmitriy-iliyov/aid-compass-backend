//package com.aidcompass.user.services;
//
//import com.aidcompass.clients.confirmation.ConfirmationService;
//import jakarta.validation.Validator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//
//@SpringBootTest(
//        properties = {
//                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration"
//        }
//)@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class UserFacadeValidationIntegrationTests {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    Validator validator;
//
//    @MockitoBean
//    UserService userService;
//
//    @MockitoBean
//    UnconfirmedUserService unconfirmedUserService;
//
//    @MockitoBean
//    ConfirmationService confirmationService;
//
//    private final String ROOT_USER_URI = "/api/users";
//    private final String UPDATE_USER_URI = ROOT_USER_URI + "/user/%s";
//
//
////    @Test
////    void updateUser_whenValidRequestWithOldEmail_shouldReturn204() throws Exception {
////        UUID id = UUID.randomUUID();
////        String invalidJson = """
////                        {
////                          "email": "test@gmail.com",
////                          "password": "test_password"
////                        }
////                        """;
////        SystemUserDto systemUserDto = new SystemUserDto(id, "test@gmail.com", "test_password", List.of(Authority.ROLE_USER), Instant.now(), Instant.now());
////
////        doReturn(new SystemUserUpdateDto(id, "test@gmail.com", "test_password", new ArrayList<>())).when(userService).mapToUpdateDto(any(UserUpdateDto.class));
////        doReturn(systemUserDto).when(userService).systemFindByEmail(any(String.class));
////        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
////        doNothing().when(userService).save(any(SystemUserDto.class));
////
////        mockMvc.perform(put(UPDATE_USER_URI.formatted(id))
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(invalidJson))
////                .andExpect(status().isNoContent());
////
////
////    }
//}
