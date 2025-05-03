package com.aidcompass.appointment;

import com.aidcompass.user.UserRepository;
import com.aidcompass.appointment.models.AppointmentResponseDto;
import com.aidcompass.user.models.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAppointmentServiceImpl implements UserAppointmentService {

    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;


    public List<AppointmentResponseDto> getAllUserAppointments(UUID id) {
        List<AppointmentResponseDto> userAppointmentsResponseDtoList = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userAppointmentsResponseDtoList.addAll(appointmentMapper.toResponseDtoList(userEntity.getAppointments()));
        return userAppointmentsResponseDtoList;
    }
}