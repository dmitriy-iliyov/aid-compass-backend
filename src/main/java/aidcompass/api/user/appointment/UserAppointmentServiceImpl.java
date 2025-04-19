package aidcompass.api.user.appointment;

import aidcompass.api.doctor.appointment.mapper.DoctorAppointmentMapper;
import aidcompass.api.general.models.appointment.AppointmentResponseDto;
import aidcompass.api.user.UserRepository;
import aidcompass.api.user.models.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAppointmentServiceImpl implements UserAppointmentService {

    private final UserRepository userRepository;
    private final DoctorAppointmentMapper doctorAppointmentMapper;


    public List<AppointmentResponseDto> getAllUserAppointments(Long id) {
        List<AppointmentResponseDto> userAppointmentsResponseDtoList = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userAppointmentsResponseDtoList.addAll(doctorAppointmentMapper.toResponseDtoList(userEntity.getAppointmentsToDoctor()));
        return userAppointmentsResponseDtoList;
    }
}