package com.aidcompass.customer;

import com.aidcompass.contracts.AuthService;
import com.aidcompass.contracts.UserOrchestrator;
import com.aidcompass.customer.models.dto.CustomerDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.services.PersistCustomerService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.enums.Authority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class PersistCustomerFacadeImpl implements PersistCustomerFacade {

    private final PersistCustomerService customerService;
    private final PersistEmptyDetailService detailService;
    private final AuthService authService;


    @Override
    public void save(UUID id) {
        DetailEntity detail = detailService.saveEmpty(id);
        customerService.save(id, detail);
    }

    @Override
    public PrivateCustomerResponseDto save(UUID id, CustomerDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(id);
        PrivateCustomerResponseDto responseDto = customerService.save(id, detail, dto);
        authService.changeAuthorityById(id, Authority.ROLE_CUSTOMER, response);
        return responseDto;
    }
}
