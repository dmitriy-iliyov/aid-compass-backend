package com.aidcompass.contact.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnconfirmedEmailService implements UnconfirmedContactService {

    private final UnconfirmedContactRepository repository;
    
}
