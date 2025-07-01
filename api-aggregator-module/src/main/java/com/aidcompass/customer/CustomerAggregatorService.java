package com.aidcompass.customer;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAggregatorService {

    private final CustomerReadService customerReadService;
    private final CustomerDeleteService customerDeleteService;
    private final AggregatorUtils utils;


    public CustomerPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        PrivateCustomerResponseDto fullDto = customerReadService.findPrivateById(id);
        return new CustomerPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id));
    }

    public void delete(UUID id) {
        utils.deleteAllAlignments(id);
        customerDeleteService.deleteById(id);
    }
}

