package com.aidcompass.user.services;

import com.aidcompass.base_dto.SystemUserDto;
import com.aidcompass.base_dto.UserResponseDto;
import com.aidcompass.base_dto.UserUpdateDto;
import com.aidcompass.enums.Authority;
import com.aidcompass.exceptions.illegal_input.IncorrectPasswordException;
import com.aidcompass.exceptions.not_found.UserNotFoundByEmailException;
import com.aidcompass.exceptions.not_found.UserNotFoundByIdException;
import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.security.core.models.authority.AuthorityService;
import com.aidcompass.security.core.models.authority.models.AuthorityEntity;
import com.aidcompass.user.mapper.UserMapper;
import com.aidcompass.user.models.DefaultUserDetails;
import com.aidcompass.user.models.SystemUserUpdateDto;
import com.aidcompass.user.models.UserEntity;
import com.aidcompass.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final AuthorityService authorityService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws BaseNotFoundException {
        UserEntity userEntity = repository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return new DefaultUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                userEntity.isExpired(),
                userEntity.isLocked()
        );
    }

    @Override
    @Transactional
    public void save(SystemUserDto systemUserDto) {
        UserEntity savedUserEntity = repository.save(mapper.toEntity(systemUserDto));
        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        savedUserEntity.getAuthorities().add(authorityEntity);
        repository.save(savedUserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByIdAndAuthority(UUID id, Authority authority) {
        return repository.existsByIdAndAuthority(id, authority);
    }

    @Override
    @Transactional
    public UserResponseDto update(SystemUserUpdateDto dto) {
        UserEntity entity = repository.findById(dto.getId()).orElseThrow(
                UserNotFoundByIdException::new
        );
        mapper.updateEntityFromDto(dto, entity);
        entity.getAuthorities().clear();
        entity.getAuthorities().addAll(authorityService.toAuthorityEntityList(dto.getAuthorities()));
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void updatePasswordByEmail(String email, String password) {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        userEntity.setPassword(passwordEncoder.encode(password));
        repository.save(userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirmByEmail(String email, Long emailId) {
        UserEntity userEntity = repository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        AuthorityEntity newAuthorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);

        List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
        authorityEntities.removeIf(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER));

        if(!authorityEntities.contains(newAuthorityEntity)) {
            authorityEntities.add(newAuthorityEntity);
        }

        userEntity.setAuthorities(authorityEntities);
        userEntity.setEmailId(emailId);
        repository.save(userEntity);
    }

    @Transactional
    @Override
    public DefaultUserDetails changeAuthorityById(UUID id, Authority authority) {
        AuthorityEntity authorityEntity = authorityService.findByAuthority(authority);
        UserEntity entity = repository.findWithAuthorityById(id).orElseThrow(UserNotFoundByIdException::new);
        List<AuthorityEntity> authorityEntityList = entity.getAuthorities();
        if (!authorityEntityList.contains(authorityEntity)) {
            authorityEntityList.clear();
            authorityEntityList.add(authorityEntity);
        }
        entity = repository.save(entity);

        return new DefaultUserDetails(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                entity.isExpired(),
                entity.isLocked()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindById(UUID id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        return mapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return mapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        return mapper.toResponseDto(userEntity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPassword(UUID id, String password) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            repository.deleteById(id);
            return;
        } throw new IncorrectPasswordException();
    }

    @Override
    public SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto) {
        return mapper.toSystemUpdateDto(userUpdateDto);
    }
}