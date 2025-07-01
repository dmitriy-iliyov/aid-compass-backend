package com.aidcompass.user.services;

import com.aidcompass.dto.user.SystemUserDto;
import com.aidcompass.dto.user.UserRegistrationDto;
import com.aidcompass.exceptions.not_found.UnconfirmedUserNotFoundByEmailException;
import com.aidcompass.user.repositories.UnconfirmedUserRepository;
import com.aidcompass.user.mapper.UserMapper;
import com.aidcompass.user.models.UnconfirmedUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnconfirmedUserServiceImpl implements UnconfirmedUserService {

    private final UnconfirmedUserRepository unconfirmedUserRepository;
    private final UserMapper userMapper;


    @Override
    public void save(UUID id, UserRegistrationDto dto) {
        unconfirmedUserRepository.save(userMapper.toUnconfirmedEntity(id, dto));
        System.out.println("user in cache: " + dto);
    }

    @Override
    public boolean existsByEmail(String email) {
        return unconfirmedUserRepository.existsById(email);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email, Long emailId) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return userMapper.toSystemDto(emailId, unconfirmedUserEntity);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return userMapper.toSystemDto(unconfirmedUserEntity);
    }

    @Override
    public void deleteByEmail(String email) {
        unconfirmedUserRepository.deleteById(email);
    }
}
