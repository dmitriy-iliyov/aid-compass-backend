package com.aidcompass.auth.user;

import com.aidcompass.auth.exceptions.not_found.UnconfirmedUserNotFoundByEmailException;
import com.aidcompass.auth.user.mapper.UserMapper;
import com.aidcompass.auth.user.models.dto.SystemUserDto;
import com.aidcompass.auth.user.models.dto.UserRegistrationDto;
import com.example.user.models.entity.UnconfirmedUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnconfirmedUserServiceImpl implements UnconfirmedUserService {

    private final UnconfirmedUserRepository unconfirmedUserRepository;
    private final UserMapper userMapper;


    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        unconfirmedUserRepository.save(userMapper.toUnconfirmedEntity(userRegistrationDto));
    }

    @Override
    public boolean existsByEmail(String email) {
        return unconfirmedUserRepository.existsById(email);
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
