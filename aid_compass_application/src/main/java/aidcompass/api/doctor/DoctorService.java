package aidcompass.api.doctor;


import aidcompass.api.doctor.mapper.DoctorMapper;
import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.doctor.models.dto.DoctorRegistrationDto;
import aidcompass.api.doctor.models.dto.DoctorResponseDto;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;
import aidcompass.api.general.utils.comparators.UsernameComparator;
import aidcompass.api.security.models.Role;
import aidcompass.api.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final DoctorMapper doctorMapper;
    private final UsernameComparator usernameComparator;


    @Transactional
    public void save(DoctorRegistrationDto doctorRegistrationDTO, Long userId){
        DoctorEntity doctorEntity = doctorMapper.toEntity(doctorRegistrationDTO);
        doctorEntity.setUser(userService.systemUpdate(userId, Role.ROLE_DOCTOR));
        doctorRepository.save(doctorEntity);
    }

    public DoctorUpdateDto mapToUpdateDto(DoctorRegistrationDto doctorRegistrationDto){
        return doctorMapper.toUpdateDto(doctorRegistrationDto);
    }

    @Transactional
    public void update(DoctorUpdateDto doctorUpdateDto){
        DoctorEntity existingDoctorEntity = doctorRepository.getReferenceById(doctorUpdateDto.getId());
        doctorMapper.updateEntityFromUpdateDto(doctorUpdateDto, existingDoctorEntity);
        doctorRepository.save(existingDoctorEntity);
    }

    @Transactional
    public void approve(Long id){
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        doctorEntity.setApproved(true);
        doctorRepository.save(doctorEntity);
    }

    @Transactional(readOnly = true)
    public boolean existingById(Long id){
        return doctorRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public DoctorResponseDto findById(Long id){
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!doctorEntity.isApproved())
            throw new IllegalArgumentException("Doctor is unapproved.");
        return doctorMapper.toResponseDto(doctorEntity);
    }

    @Transactional(readOnly = true)
    public DoctorResponseDto findByUsername(String username){
        DoctorEntity doctorEntity = doctorRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        if (!doctorEntity.isApproved())
            throw new IllegalArgumentException("Doctor is unapproved.");
        return doctorMapper.toResponseDto(doctorEntity);
    }

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAllUnapproved(){
        List<DoctorResponseDto> doctorResponseDtoList = doctorMapper.toResponseDtoList(doctorRepository.findAllByApproved(false));
        doctorResponseDtoList.sort(usernameComparator);
        return doctorResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAllApprovedBySpecialization(String specialization){
        List<DoctorResponseDto> doctorResponseDtoList = doctorMapper.toResponseDtoList(
                doctorRepository.findAllByApprovedAndSpecialization(true, specialization));
        doctorResponseDtoList.sort(usernameComparator);
        return doctorResponseDtoList;
    }

    @Transactional
    public void deleteById(Long id){
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userService.systemUpdate(doctorEntity.getUser().getId(), Role.ROLE_USER);
        doctorRepository.deleteById(id);
    }
}
