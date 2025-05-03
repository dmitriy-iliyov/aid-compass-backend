package com.aidcompass.client;

import com.aidcompass.client.dto.UserResponseDto;
import com.aidcompass.client.dto.UserUpdateDto;
import com.aidcompass.global_exceptions.UserNotFoundException;
import com.aidcompass.global_exceptions.dto.ExceptionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
public class RestClientAuthService implements AuthService {

    private final RestClient restClient;


    @Override
    public UserResponseDto findById(UUID id) {
        try {
            return restClient.get().uri("/" + id).retrieve().body(UserResponseDto.class);
        } catch (HttpClientErrorException e) {
            ExceptionResponseDto response = e.getResponseBodyAs(ExceptionResponseDto.class);
            log.error(String.valueOf(response));
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void update(UUID id, UserUpdateDto user) {
        try {
            restClient.put().uri("/" + id).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            log.error(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            restClient.delete().uri("/" + id).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            log.error(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteByPassword(UUID id, String password) {
        restClient.delete().uri("/" + id + "/" + password).retrieve().toBodilessEntity();
    }

    @Override
    public boolean existsById(UUID ownerId) {
        return true;
    }
}
