package com.aidcompass.confirmation;


public interface ContactConfirmationFacade {

    void sendConfirmationMessage(String resource, Long resourceId, ResourceType type) throws Exception;

    void validateConfirmationToken(String token, ResourceType type);
}
