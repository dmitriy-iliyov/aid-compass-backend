package com.aidcompass;

import com.aidcompass.work_day.services.WorkDayService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final WorkDayService service;


    @GetMapping("/{owner_id}/weak")
    public ResponseEntity<?> getWeak(@PathVariable("owner_id") UUID ownerId,
                                     @RequestParam("current_date")
                                     @DateTimeFormat(pattern = "yyyy-MM-dd")
                                     @NotNull(message = "Date shouldn't be null;")
                                     LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findWeakByDate(ownerId, date));
    }

    @GetMapping("/{owner_id}/month")
    public ResponseEntity<?> getMonth(@PathVariable("owner_id") UUID ownerId,
                                      @RequestParam("current_date")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd")
                                      @NotNull(message = "Date shouldn't be null;")
                                      LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findMonthByDate(ownerId, date));
    }
}
