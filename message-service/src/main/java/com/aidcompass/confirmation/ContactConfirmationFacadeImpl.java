package com.aidcompass.confirmation;

import com.aidcompass.confirmation.services.ResourceConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ContactConfirmationFacadeImpl implements ContactConfirmationFacade {

    private final Map<String, ResourceConfirmationService> services;


    @Autowired
    public ContactConfirmationFacadeImpl(List<ResourceConfirmationService> resourceConfirmationServiceList) {
        this.services = resourceConfirmationServiceList.stream()
                .collect(Collectors.toMap(ResourceConfirmationService::getType, Function.identity()));
    }

    @Override
    public void sendConfirmationMessage(String resource, Long resourceId, ResourceType type) throws Exception {
        this.services.get(type.toString())
                .sendConfirmationMessage(resource, resourceId);
    }

    @Override
    public void validateConfirmationToken(String token, ResourceType type) {
        this.services.get(type.toString())
                .validateConfirmationToken(token);
    }
}
