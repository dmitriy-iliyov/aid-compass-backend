package aidcompass.api.doctor.appointment;

import aidcompass.api.doctor.DoctorRepository;
import aidcompass.api.doctor.appointment.mapper.DoctorAppointmentMapper;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
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
    public void save(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto) throws IllegalArgumentException {
        DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointmentMapper
                .toEntity(doctorAppointmentRegistrationDto);
        UserEntity user = userRepository.findById(doctorAppointmentRegistrationDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        DoctorEntity doctor = doctorRepository.findById(doctorAppointmentRegistrationDto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found."));
        if (!doctor.isApproved()) {
            throw new IllegalArgumentException("Doctor is unapproved.");
        }
        doctorAppointmentEntity.setUser(user);
        doctorAppointmentEntity.setVolunteer(doctor);
        doctorAppointmentRepository.save(doctorAppointmentEntity);
    }

    @Transactional
    public void update(DoctorAppointmentUpdateDto doctorAppointmentUpdateDto) {
        DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointmentRepository.findById(
                doctorAppointmentUpdateDto.getId()).orElseThrow(() -> new EntityNotFoundException("Appointment not found."));
        doctorAppointmentMapper.updateEntityFromUpdateDto(doctorAppointmentUpdateDto, doctorAppointmentEntity);
        doctorAppointmentRepository.save(doctorAppointmentEntity);
    }

    @Transactional(readOnly = true)
    public boolean existingByDoctorNUserId(Long userId, Long doctorId) {
        if (userId == null || doctorId == null)
            throw new IllegalArgumentException("User and doctor id can't be empty.");
        return userRepository.existsById(userId) && doctorRepository.existsById(doctorId);
    }

    @Transactional(readOnly = true)
    public List<DoctorAppointmentResponseDto> findAllByDoctorId(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found."));
        return doctorAppointmentMapper.toResponseDtoList(doctorAppointmentRepository.findAllByVolunteer(doctorEntity));
    }

    @Transactional(readOnly = true)
    public List<DoctorAppointmentResponseDto> findAllByUserId(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        return doctorAppointmentMapper.toResponseDtoList(doctorAppointmentRepository.findAllByUser(userEntity));
    }

    @Transactional(readOnly = true)
    public DoctorAppointmentResponseDto findById(Long id) {
        return doctorAppointmentMapper.toResponseDto(doctorAppointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Appointment not found.")));
    }

    @Transactional
    public void deleteById(Long id) {
        doctorAppointmentRepository.deleteById(id);
    }
}
