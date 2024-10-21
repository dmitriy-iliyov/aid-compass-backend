package aidcompass.api.doctor.appointment;

import aidcompass.api.doctor.DoctorRepository;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentUpdateDto;
import aidcompass.api.doctor.appointment.mapper.DoctorAppointmentMapper;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.user.UserRepository;
import aidcompass.api.user.models.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAppointmentServices {

    private final DoctorAppointmentRepository doctorAppointmentRepository;

    private final DoctorAppointmentMapper doctorAppointmentMapper;

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    @Transactional
    public void save(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto) throws IllegalArgumentException{
        DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointmentMapper
                .toEntity(doctorAppointmentRegistrationDto);
        UserEntity user = userRepository.findById(doctorAppointmentRegistrationDto.getUserId())
                .orElseThrow(EntityNotFoundException::new);
        DoctorEntity doctor = doctorRepository.findById(doctorAppointmentRegistrationDto.getDoctorId())
                .orElseThrow(EntityNotFoundException::new);
        if (!doctor.isApproved())
            throw new IllegalArgumentException("Doctor is unapproved!");
        doctorAppointmentEntity.setUser(user);
        doctorAppointmentEntity.setVolunteer(doctor);
        doctorAppointmentRepository.save(doctorAppointmentEntity);
    }

    @Transactional
    public void update(DoctorAppointmentUpdateDto doctorAppointmentUpdateDto){
        DoctorAppointmentEntity exitingDoctorAppointmentEntity = doctorAppointmentRepository.getReferenceById(
                doctorAppointmentUpdateDto.getId());
        doctorAppointmentMapper.updateEntityFromUpdateDto(doctorAppointmentUpdateDto, exitingDoctorAppointmentEntity);
        doctorAppointmentRepository.save(exitingDoctorAppointmentEntity);
    }

    public boolean existingByDoctorNUserId(Long userId, Long doctorId){
        if (userId == null || doctorId == null)
            return false;
        return userRepository.existsById(userId) && doctorRepository.existsById(doctorId);
    }

    public List<DoctorAppointmentResponseDto> findAllByDoctorId(Long id) throws EntityNotFoundException{
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return doctorAppointmentMapper.toResponseDtoList(doctorAppointmentRepository.findAllByVolunteer(doctorEntity));
    }

    public List<DoctorAppointmentResponseDto> findAllByUserId(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return doctorAppointmentMapper.toResponseDtoList(doctorAppointmentRepository.findAllByUser(userEntity));
    }

    public DoctorAppointmentResponseDto findById(Long id){
        return doctorAppointmentMapper.toResponseDto(doctorAppointmentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public void deleteById(Long id){
        doctorAppointmentRepository.deleteById(id);
    }
}
