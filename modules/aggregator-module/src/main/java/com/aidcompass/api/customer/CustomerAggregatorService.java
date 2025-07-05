package com.aidcompass.api.customer;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.customer.services.CustomerService;
import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(noRollbackFor = BaseNotFoundException.class)
    public void delete(UUID id) {
        utils.deleteAllUserAlignments(id);
        customerService.deleteById(id);
    }
}

