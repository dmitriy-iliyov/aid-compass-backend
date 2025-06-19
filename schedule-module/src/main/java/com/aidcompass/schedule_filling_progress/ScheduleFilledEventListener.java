package com.aidcompass.schedule_filling_progress;

import com.aidcompass.enums.RoleToServiceTypeMapper;
import com.aidcompass.enums.ServiceType;
import com.aidcompass.interfaces.ProfileStatusUpdateFacade;
import com.aidcompass.schedule_filling_progress.models.ScheduleFilledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleFilledEventListener {

    private final ProfileStatusUpdateFacade profileStatusUpdateFacade;


    @TransactionalEventListener
    public void notifyAboutComplete(ScheduleFilledEvent event) {
        ServiceType type = RoleToServiceTypeMapper.from(event.authority());
        profileStatusUpdateFacade.updateProfileStatus(type, event.userId());
    }
}
