package com.aidcompass.work_interval;

import com.aidcompass.exceptions.work_interval.WorkIntervalAlreadyExistsException;
import com.aidcompass.exceptions.work_interval.WorkIntervalsInExistedIntervalException;
import com.aidcompass.exceptions.work_interval.ReactionNotFoundException;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.models.overlaps.GlueType;
import com.aidcompass.work_interval.services.WorkIntervalService;
import com.aidcompass.work_interval.validation.duration.DurationValidator;
import com.aidcompass.work_interval.models.overlaps.ValidationInfoDto;
import com.aidcompass.work_interval.models.overlaps.ValidationStatus;
import com.aidcompass.work_interval.validation.ownership.OwnershipValidator;
import com.aidcompass.work_interval.validation.overlaps.OverlapsValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class WorkIntervalOrchestrator {

    private final WorkIntervalService service;
    private final OwnershipValidator ownershipValidator;
    private final DurationValidator durationValidator;
    private final OverlapsValidator overlapsValidator;
    private Map<ValidationStatus, Function<ValidationInfoDto, WorkIntervalResponseDto>> reactionsMap;


    @PostConstruct
    public void setUpReactions() {
        this.reactionsMap = new HashMap<>();

        reactionsMap.put(
                ValidationStatus.ALREADY_EXISTING,
                (ValidationInfoDto info) -> {
                    throw new WorkIntervalAlreadyExistsException();
                }
        );

        reactionsMap.put(
                ValidationStatus.EXISTING_START_IS_IN_PASSED,
                (ValidationInfoDto info) -> service.glue(info.dto(), info.id(), GlueType.BEFORE)
        );

        reactionsMap.put(
                ValidationStatus.EXISTING_END_IS_IN_PASSED,
                (ValidationInfoDto info) -> service.glue(info.dto(), info.id(), GlueType.AFTER)
        );

        reactionsMap.put(
                ValidationStatus.EXISTING_START_IS_PASSED_END,
                (ValidationInfoDto info) -> service.glue(info.dto(), info.id(), GlueType.BEFORE)
        );

        reactionsMap.put(
                ValidationStatus.EXISTING_END_IS_PASSED_START,
                (ValidationInfoDto info) -> service.glue(info.dto(), info.id(), GlueType.AFTER)
        );

        reactionsMap.put(
                ValidationStatus.EXISTING_INTERVAL_IS_INSIDE_PASSED,
                (ValidationInfoDto info) -> service.update(
                        new WorkIntervalUpdateDto(
                                info.id(),
                                info.dto().start(),
                                info.dto().end(),
                                info.dto().date()
                        )
                )
        );

        reactionsMap.put(
                ValidationStatus.PASSED_INTERVAL_IS_INSIDE_EXISTING,
                (ValidationInfoDto info) -> {
                    throw new WorkIntervalsInExistedIntervalException();
                }
        );
    }

    public WorkIntervalResponseDto save(UUID ownerId, WorkIntervalCreateDto dto) {
        durationValidator.validateWorkIntervalDuration(ownerId, dto);
        ValidationInfoDto info = overlapsValidator.checkForOverlaps(ownerId, dto);

        if (info.status() == ValidationStatus.OK) {
            return service.save(ownerId, dto);
        }

        Function<ValidationInfoDto, WorkIntervalResponseDto> reaction = reactionsMap.get(info.status());
        if (reaction != null) {
            return reaction.apply(info);
        }

        throw new ReactionNotFoundException();
    }

    public WorkIntervalResponseDto update(UUID ownerId, WorkIntervalUpdateDto dto) {
        ownershipValidator.assertOwnership(ownerId, dto.id());
        durationValidator.validateWorkIntervalDuration(ownerId, dto);
        ValidationInfoDto info = overlapsValidator.checkForOverlaps(ownerId, dto);

        if (info.status() == ValidationStatus.OK) {
            return service.update(dto);
        }

        Function<ValidationInfoDto, WorkIntervalResponseDto> reaction = reactionsMap.get(info.status());
        if (reaction != null) {
            return reaction.apply(info);
        }

        throw new ReactionNotFoundException();
    }

    public void delete(UUID ownerId, Long id) {
        ownershipValidator.assertOwnership(ownerId, id);
        service.deleteById(id);
    }
}
