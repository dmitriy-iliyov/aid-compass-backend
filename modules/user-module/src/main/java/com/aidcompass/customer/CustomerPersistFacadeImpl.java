package com.aidcompass.customer;

import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.customer.services.CustomerPersistService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.security.auth.services.UserAuthService;
import com.aidcompass.security.domain.authority.models.Authority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class CustomerPersistFacadeImpl implements CustomerPersistFacade {

    private final CustomerPersistService customerService;
    private final PersistEmptyDetailService detailService;
    private final UserAuthService userAuthService;


    @Transactional
    @Override
    public void save(UUID id) {
        DetailEntity detail = detailService.saveEmpty(id);
        customerService.save(id, detail);
    }

    @Transactional
    @Override
    public PrivateCustomerResponseDto save(UUID id, CustomerDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(id);
        PrivateCustomerResponseDto responseDto = customerService.save(id, detail, dto);
        userAuthService.changeAuthorityById(id, Authority.ROLE_CUSTOMER, response);
        return responseDto;
    }
}
