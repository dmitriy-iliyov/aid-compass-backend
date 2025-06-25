package com.aidcompass.pass_recovery;

import com.aidcompass.base_dto.security.RecoveryRequestDto;
import com.aidcompass.contracts.UserOrchestrator;
import com.aidcompass.exceptions.models.InvalidPasswordRecoveryTokenException;
import com.aidcompass.message_services.MessageFactory;
import com.aidcompass.message_services.MessageService;
import com.aidcompass.utils.CodeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    private final MessageService messageService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserOrchestrator userOrchestrator;


    public EmailPasswordRecoveryService(@Qualifier("emailMessageService") MessageService messageService,
                                        PasswordRecoveryRepository passwordRecoveryRepository,
                                        UserOrchestrator userOrchestrator) {
        this.messageService = messageService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.userOrchestrator = userOrchestrator;
    }

    @Override
    public void sendRecoveryMessage(String recipientResource) throws Exception {
        String code = CodeFactory.generate();
        messageService.sendMessage(MessageFactory.passwordRecovery(recipientResource, code));
        passwordRecoveryRepository.save(code, recipientResource);
    }

    @Override
    public void recoverPassword(String code, String password) {
        String email = passwordRecoveryRepository.findAndDeleteByToken(code).orElseThrow(
                InvalidPasswordRecoveryTokenException::new
        );
        userOrchestrator.recoverPasswordByEmail(new RecoveryRequestDto(email, password));
    }
}
