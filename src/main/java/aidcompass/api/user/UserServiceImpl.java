package aidcompass.api.user;

import aidcompass.api.security.models.Role;
import aidcompass.api.user.mapper.UserMapper;
import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.user.models.dto.UserUpdateDto;
import jakarta.persistence.EntityNotFoundException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
//                () -> new UsernameNotFoundException("User with " + email + " not found."));
//        return new SecurityUserDetails(
//                userEntity.getEmail(),
//                userEntity.getPassword(),
//                Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().toString())));
//    }

    @Transactional
    public void save(UserRegistrationDto customerRegistrationDto) {
        UserEntity customerEntity = userMapper.toEntity(customerRegistrationDto);
        userRepository.save(customerEntity);
    }

    @Transactional
    public void confirmByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        userEntity.setRole(Role.ROLE_USER);
        userRepository.save(userEntity);
    }

    public UserUpdateDto mapToUpdateDto(UserRegistrationDto userRegistrationDto){
        return userMapper.toUpdateDto(userRegistrationDto);
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findById(userUpdateDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        userMapper.updateEntityFromUpdateDto(userUpdateDto, userEntity);
        userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity systemUpdate(Long id, Role role) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        userEntity.setRole(role);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public boolean existingById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        UserEntity customerEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        return userMapper.toResponseDto(customerEntity);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

//    @Transactional
//    public void deleteByPassword(String password) throws BadCredentialsException {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        SecurityUserEntity securityUserEntity = securityUserRepository.findByEmail(email).orElseThrow(
//                () -> new EntityNotFoundException("User not found."));
//        if (passwordEncoder.matches(password, securityUserEntity.getPassword())) {
//            securityUserRepository.deleteByEmail(email);
//            logger.info("User successfully deleted.");
//        } else
//            throw new BadCredentialsException("Invalid password");
//    }
}
