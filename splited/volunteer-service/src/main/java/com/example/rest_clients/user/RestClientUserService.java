package com.example.rest_clients.user;

import com.example.exceptions.dto.ExceptionResponseDto;
import com.example.exceptions.user.UserNotFoundByIdException;
import com.example.rest_clients.user.dto.UserResponseDto;
import com.example.rest_clients.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RestClientUserService implements UserService {

    private final RestClient restClient;


    @Override
    public UserResponseDto findById(UUID id) {
        try {
            return restClient.get().uri("/" + id).retrieve().body(UserResponseDto.class);
        } catch (HttpClientErrorException e) {
            ExceptionResponseDto response = e.getResponseBodyAs(ExceptionResponseDto.class);
            log.error(String.valueOf(response));
            throw new UserNotFoundByIdException(e.getMessage());
        }
    }

    @Override
    public void update(UUID id, UserUpdateDto user) {
        try {
            restClient.put().uri("/" + id).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            log.error(e.getMessage());
            throw new UserNotFoundByIdException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            restClient.delete().uri("/" + id).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            log.error(e.getMessage());
            throw new UserNotFoundByIdException(e.getMessage());
        }
    }

    @Override
    public void deleteByPassword(UUID id, String password) {
        restClient.delete().uri("/" + id + "/" + password).retrieve().toBodilessEntity();
    }
}
