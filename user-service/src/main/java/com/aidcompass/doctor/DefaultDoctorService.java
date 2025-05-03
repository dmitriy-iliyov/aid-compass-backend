package com.aidcompass.doctor;


import com.aidcompass.doctor.models.dto.DoctorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultDoctorService implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserServiceImpl userService;
    private final DoctorMapper doctorMapper;
    private final UsernameComparator usernameComparator;


    @Override
    @Transactional
    public void save(DoctorRegistrationDto doctorRegistrationDTO, Long userId) {
        DoctorEntity doctorEntity = doctorMapper.toEntity(doctorRegistrationDTO);
        doctorEntity.setUser(userService.systemUpdate(userId, Role.ROLE_DOCTOR));
        doctorRepository.save(doctorEntity);
    }

    @Override
    public DoctorUpdateDto mapToUpdateDto(DoctorRegistrationDto doctorRegistrationDto) {
        return doctorMapper.toUpdateDto(doctorRegistrationDto);
    }

    @Override
    @Transactional
    public void update(DoctorUpdateDto doctorUpdateDto) {
        DoctorEntity doctorEntity = doctorRepository.findById(doctorUpdateDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        doctorMapper.updateEntityFromUpdateDto(doctorUpdateDto, doctorEntity);
        doctorRepository.save(doctorEntity);
    }

    @Override
    @Transactional
    public void approve(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        doctorEntity.setApproved(true);
        doctorRepository.save(doctorEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existingById(Long id) {
        return doctorRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public DoctorResponseDto findById(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        if (!doctorEntity.isApproved())
            throw new IllegalArgumentException("Doctor is unapproved.");
        return doctorMapper.toResponseDto(doctorEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDto findByUsername(String username) {
        DoctorEntity doctorEntity = doctorRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        if (!doctorEntity.isApproved())
            throw new IllegalArgumentException("Doctor is unapproved.");
        return doctorMapper.toResponseDto(doctorEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAllUnapproved() {
        List<DoctorResponseDto> doctorResponseDtoList = doctorMapper.toResponseDtoList(doctorRepository.findAllByApproved(false));
        doctorResponseDtoList.sort(usernameComparator);
        return doctorResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAllApproved() {
        List<DoctorResponseDto> doctorResponseDtoList = doctorMapper.toResponseDtoList(
                doctorRepository.findAllByApproved(true));
        doctorResponseDtoList.sort(usernameComparator);
        return doctorResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAllApprovedBySpecialization(String specialization) {
        List<DoctorResponseDto> doctorResponseDtoList = doctorMapper.toResponseDtoList(
                doctorRepository.findAllByApprovedAndSpecialization(true, specialization));
        doctorResponseDtoList.sort(usernameComparator);
        return doctorResponseDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        userService.systemUpdate(doctorEntity.getUser().getId(), Role.ROLE_USER);
        doctorRepository.deleteById(id);
    }
}
