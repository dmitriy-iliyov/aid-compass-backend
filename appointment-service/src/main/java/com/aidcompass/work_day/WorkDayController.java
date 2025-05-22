package com.aidcompass.work_day;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_day.services.WorkDayService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/days")
@RequiredArgsConstructor
public class WorkDayController {

    private final WorkDayService service;
    private final WorkDayFacade facade;


    @PostMapping("/{owner_id}")
    public ResponseEntity<?> createWorkDay(@PathVariable("owner_id") UUID ownerId,
                                           @RequestBody @Valid WorkDayCreateDto dto,
                                           @RequestParam(value = "return_body", defaultValue = "false")
                                           boolean returnBody) {
        WorkDayResponseDto response = facade.save(ownerId, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{owner_id}")
    public ResponseEntity<?> getWorkDay(@PathVariable("owner_id") UUID ownerId,
                                        @RequestParam("date")
                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                        @NotNull(message = "Date shouldn't be null!")
                                        LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findDayByOwnerIdAndDate(ownerId, date));
    }

    @PutMapping("/{owner_id}")
    public ResponseEntity<?> updateWorkDay(@PathVariable("owner_id") UUID ownerId,
                                           @RequestBody @Valid WorkDayUpdateDto dto,
                                           @RequestParam(value = "return_body", defaultValue = "false")
                                           boolean returnBody) {
        WorkDayResponseDto response = facade.update(ownerId, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{owner_id}")
    public ResponseEntity<?> deleteWorkDay(@PathVariable("owner_id") UUID ownerId,
                                           @RequestParam("date")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                           @NotNull(message = "Date shouldn't be null;")
                                           LocalDate date) {
        service.delete(ownerId, date);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
