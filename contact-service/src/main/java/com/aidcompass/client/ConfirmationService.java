package com.aidcompass.client;

import com.aidcompass.client.models.ConfirmationRequestDto;

public interface ConfirmationService {

    void sendConfirmationRequest(ConfirmationRequestDto request);

}
