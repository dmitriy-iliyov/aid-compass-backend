package com.aidcompass.customer;


import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/empty/{id}")
    public ResponseEntity<?> createEmptyCustomer(@PathVariable("id") UUID id) {
        customerService.save(id);
        //перепревязать контпкт аккаунта к польззователю
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createCustomer(@PathVariable("id") UUID id,
                                            @RequestBody @Valid CustomerRegistrationDto customerRegistrationDto,
                                            @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody) {
        PrivateCustomerResponseDto response = customerService.save(id, customerRegistrationDto);
        //перепревязать контпкт аккаунта к польззователю
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}/public")
    public ResponseEntity<?> getPublicCustomer(@PathVariable("id") UUID id) {
        PublicCustomerResponseDto publicCustomerResponseDto = customerService.findPublicById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicCustomerResponseDto);
    }

    @GetMapping("/{id}/private")
    public ResponseEntity<?> getPrivateCustomer(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.findPrivateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") UUID id,
                                            @RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
        customerService.updateById(id, customerUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
