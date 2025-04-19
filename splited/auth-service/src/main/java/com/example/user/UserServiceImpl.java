package com.example.user;

import com.example.authority.AuthorityService;
import com.example.authority.models.Authority;
import com.example.authority.models.AuthorityEntity;
import com.example.exceptions.illegal_input.IncorrectPasswordException;
import com.example.exceptions.not_found.UserNotFoundByEmailException;
import com.example.exceptions.not_found.UserNotFoundByIdException;
import com.example.general.PasswordEncoder;
import com.example.user.mapper.UserMapper;
import com.example.user.models.dto.SystemUserDto;
import com.example.user.models.dto.SystemUserUpdateDto;
import com.example.user.models.dto.UserResponseDto;
import com.example.user.models.dto.UserUpdateDto;
import com.example.user.models.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
//        return new DefaultUserDetails(
//                userEntity.getId(),
//                userEntity.getEmail(),
//                userEntity.getPassword(),
//                userEntity.getAuthorities().stream()
//                        .map(AuthorityEntity::getAuthority)
//                        .map(Authority::getAuthority)
//                        .map(SimpleGrantedAuthority::new)
//                        .toList(),
//                userEntity.isExpired(),
//                userEntity.isLocked());
//    }

    @Override
    @Transactional
    public void save(SystemUserDto systemUserDto) {
        UserEntity savedUserEntity = userRepository.save(userMapper.toEntity(systemUserDto));

        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        savedUserEntity.getAuthorities().add(authorityEntity);

        userRepository.save(savedUserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void update(SystemUserUpdateDto systemUserUpdateDto) {
        UserEntity userEntity = userRepository.findById(systemUserUpdateDto.getId()).orElseThrow(
                UserNotFoundByIdException::new
        );
        userMapper.updateEntityFromDto(systemUserUpdateDto, userEntity);
        userEntity.setAuthorities(authorityService.toAuthorityEntityList(systemUserUpdateDto.getAuthorities()));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void updatePasswordByEmail(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirmByEmail(String email) {
        UserEntity userEntity = userRepository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        AuthorityEntity newAuthorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);

        List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
        authorityEntities.removeIf(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER));

        if(!authorityEntities.contains(newAuthorityEntity)) {
            authorityEntities.add(newAuthorityEntity);
        }

        userEntity.setAuthorities(authorityEntities);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundByEmailException::new);
        return userMapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return userMapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        return userMapper.toResponseDto(userEntity);
    }

//    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(
//            value = "users",
//            key = "'pn' + #pageRequest.pageNumber + ':' + 'ps' + #pageRequest.pageSize + ':' + " +
//                    "T(org.springframework.util.DigestUtils).md5DigestAsHex(#filter.toString().getBytes())",
//            condition = "#pageRequest.pageNumber >= 0 && #pageRequest.pageNumber <= 2"
//    )
//    public PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest) {
//        Page<UserEntity> page = userRepository.findAll(pageRequest);
//        return PagedDataDto.toPagedDataDto(
//                userMapper.toPublicResponseDto(page.getContent()),
//                page.getTotalElements()
//        );
//    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPassword(UUID id, String password) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            userRepository.deleteById(id);
            return;
        } throw new IncorrectPasswordException();
    }

    @Override
    public SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto) {
        return userMapper.toSystemUpdateDto(userUpdateDto);
    }
}