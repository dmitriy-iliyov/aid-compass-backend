package com.aidcompass;

import com.aidcompass.appointment.services.SystemAppointmentService;
import com.aidcompass.interval.services.SystemIntervalService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system/v1")
//@PreAuthorize("hasAuthority('ROLE_SCHEDULE_TASK_SERVICE')")
@RequiredArgsConstructor
public class ScheduleSystemController {

    private final SystemIntervalService systemIntervalService;
    private final SystemAppointmentService systemAppointmentService;


    @DeleteMapping("/intervals/past/batch")
    public ResponseEntity<?> deleteIntervals(@RequestParam("batch_size")
                                             @Positive(message = "Batch size should be positive!")
                                             @Min(value = 20, message = "Min batch size value is 20!")
                                             int batchSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(systemIntervalService.deleteBatchBeforeWeakStart(batchSize));
    }

    @PatchMapping("/appointments/past/batch/skip")
    public ResponseEntity<?> markAppointmentBatchAsSkipped(@RequestParam("batch_size")
                                                           @Positive(message = "Batch size should be positive!")
                                                           @Min(value = 20, message = "Min batch size value is 20!")
                                                           int batchSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(systemAppointmentService.markBatchAsSkipped(batchSize));
    }
}
