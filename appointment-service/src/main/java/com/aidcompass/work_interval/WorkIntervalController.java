package com.aidcompass.work_interval;

import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.services.WorkIntervalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/intervals")
@RequiredArgsConstructor
public class WorkIntervalController {

    private final WorkIntervalService service;
    private final WorkIntervalOrchestrator orchestrator;


    @PostMapping("/{owner_id}")
    public ResponseEntity<?> createWorkInterval(@PathVariable("owner_id") UUID ownerId,
                                                @RequestBody @Valid WorkIntervalCreateDto dto,
                                                @RequestParam(value = "return_body", defaultValue = "false")
                                                boolean returnBody) {
        WorkIntervalResponseDto response = service.save(ownerId, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{owner_id}")
    public ResponseEntity<?> updateWorkInterval(@PathVariable("owner_id") UUID ownerId,
                                                @RequestBody @Valid WorkIntervalUpdateDto dto,
                                                @RequestParam(value = "return_body", defaultValue = "false")
                                                boolean returnBody) {
        WorkIntervalResponseDto response = orchestrator.update(ownerId, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkInterval(@PathVariable("id")
                                             @NotNull(message = "Id shouldn't be null!")
                                             @Positive(message = "Id should be positive!")
                                             Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{owner_id}/{id}")
    public ResponseEntity<?> deleteWorkInterval(@PathVariable("owner_id") UUID ownerId,
                                                @PathVariable("id")
                                                @NotNull(message = "Id shouldn't be null!")
                                                @Positive(message = "Id should be positive!")
                                                Long id) {
        orchestrator.delete(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
