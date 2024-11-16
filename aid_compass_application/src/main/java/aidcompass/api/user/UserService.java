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

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
//                () -> {
//                    logger.error("User with email {} not found.", email);
//                    return new UsernameNotFoundException("User with %s not found." + email);
//                });
//        logger.info("UserEntity: {}", userEntity);
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

    public UserUpdateDto mapToUpdateDto(UserRegistrationDto userRegistrationDto){
        return userMapper.toUpdateDto(userRegistrationDto);
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto){
        UserEntity existingUserEntity = userRepository.getReferenceById(userUpdateDto.getId());
        userMapper.updateEntityFromUpdateDto(userUpdateDto, existingUserEntity);
        userRepository.save(existingUserEntity);
    }

    @Transactional
    public UserEntity systemUpdate(Long id, Role role){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userEntity.setRole(role);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public boolean existingById(Long id){
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        UserEntity customerEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return userMapper.toResponseDto(customerEntity);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll(){
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    @Transactional
    public void deleteByEmail(String email){
        userRepository.deleteByEmail(email);
    }

    //    @Transactional
//    public void deleteByPassword(String password) throws BadCredentialsException {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        SecurityUserEntity securityUserEntity = securityUserRepository.findByEmail(email)
//                .orElseThrow(EntityNotFoundException::new);
//        if (passwordEncoder.matches(password, securityUserEntity.getPassword())) {
//            securityUserRepository.deleteByEmail(email);
//            logger.info("User successfully deleted.");
//        } else
//            throw new BadCredentialsException("Invalid password");
//    }
}
