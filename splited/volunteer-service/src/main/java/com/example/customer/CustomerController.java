package com.example.customer;


import com.example.contacts.models.ContactsDto;
import com.example.customer.models.dto.CustomerRegistrationDto;
import com.example.customer.models.dto.CustomerUpdateDto;
import com.example.customer.models.dto.PublicCustomerResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/{id}")
    public ResponseEntity<?> createCustomer(@PathVariable("id") UUID id,
                                            @RequestBody @Valid CustomerRegistrationDto customerRegistrationDto) {
        customerService.save(id, customerRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getPublicCustomer(@PathVariable("id") UUID id) {
        PublicCustomerResponseDto publicCustomerResponseDto = customerService.findPublicById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicCustomerResponseDto);
    }

    @GetMapping("/private/{id}")
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

    @PatchMapping("/{id}/contacts")
    public ResponseEntity<?> updateCustomerContacts(@PathVariable("id") UUID id,
                                                    @RequestBody @Valid ContactsDto contactsDto) {
        customerService.updateContactsById(id, contactsDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}/{password}")
    public ResponseEntity<?> deleteCustomerByPassword(@PathVariable("id") UUID id,
                                                      @PathVariable("password")
                                                      @NotBlank(message = "Password shouldn't be empty!") String password) {
        customerService.deleteByPassword(id, password);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
