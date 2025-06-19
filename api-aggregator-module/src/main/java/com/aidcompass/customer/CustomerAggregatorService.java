package com.aidcompass.customer;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.appointment.services.AppointmentService;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAggregatorService {

    private final CustomerService customerService;
    private final AggregatorUtils utils;


    public CustomerPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        PrivateCustomerResponseDto fullDto = customerService.findPrivateById(id);
        return new CustomerPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id));
    }

    public void delete(UUID id) {
        utils.deleteAllAlignments(id);
        customerService.deleteById(id);
    }
}

