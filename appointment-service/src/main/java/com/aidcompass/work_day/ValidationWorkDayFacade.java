package com.aidcompass.work_day;

import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_day.services.DeleteWorkDayService;
import com.aidcompass.work_day.services.UpdateWorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationWorkDayFacade {

    private final UpdateWorkDayService updateService;
    private final DeleteWorkDayService deleteService;


    public WorkDayResponseDto update(UUID ownerId, WorkDayUpdateDto dto) {
        // validation ownership
        return updateService.update(dto);
    }

    public void delete(UUID ownerId, LocalDate date) {
        deleteService.delete(ownerId, date);
    }
}
