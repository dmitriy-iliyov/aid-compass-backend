package com.aidcompass.work_interval.validation.overlaps;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.models.dto.SystemWorkIntervalDto;
import com.aidcompass.work_interval.models.overlaps.ValidationInfoDto;
import com.aidcompass.work_interval.models.overlaps.ValidationStatus;
import com.aidcompass.work_interval.services.WorkIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OverlapsValidatorImpl implements OverlapsValidator {


    private final WorkIntervalService service;


    @Override
    public ValidationInfoDto checkForOverlaps(UUID ownerId, WorkIntervalMarker newInterval) {
        try {
            List<SystemWorkIntervalDto> existingDtoList = service.systemFindAllByOwnerIdAndDate(ownerId, newInterval.date());

            for (SystemWorkIntervalDto existedInterval: existingDtoList) {
                // e |------|
                // n |------|
                if (existedInterval.equals(newInterval)) {
                    return new ValidationInfoDto(ValidationStatus.ALREADY_EXISTING, null, null);
                }
                // e     |------|
                // n |------|
                if (existedInterval.start().isAfter(newInterval.start()) &&
                        existedInterval.start().isBefore(newInterval.end())) {
                    return new ValidationInfoDto(ValidationStatus.EXISTING_START_IS_IN_PASSED, newInterval, existedInterval.id());
                }
                // e |------|
                // n     |------|
                if (existedInterval.end().isAfter(newInterval.start()) &&
                    existedInterval.end().isBefore(newInterval.end())) {
                    return new ValidationInfoDto(ValidationStatus.EXISTING_END_IS_IN_PASSED, newInterval, existedInterval.id());
                }
                // e   |---|
                // n |-------|
                if (existedInterval.start().isAfter(newInterval.start()) &&
                        existedInterval.end().isBefore(newInterval.end())) {
                    return new ValidationInfoDto(ValidationStatus.EXISTING_INTERVAL_IS_INSIDE_PASSED, newInterval, existedInterval.id());
                }
                // e |-------|
                // n   |---|
                if (!existedInterval.start().isAfter(newInterval.start()) &&
                    !existedInterval.end().isBefore(newInterval.end())) {
                    return new ValidationInfoDto(ValidationStatus.PASSED_INTERVAL_IS_INSIDE_EXISTING, null, null);
                }
                // e        |------|
                // n |------|
                if (existedInterval.start().equals(newInterval.end())) {
                    return new ValidationInfoDto(ValidationStatus.EXISTING_START_IS_PASSED_END, newInterval, existedInterval.id());
                }
                // e |------|
                // n        |------|
                if (existedInterval.end().equals(newInterval.start())) {
                    return new ValidationInfoDto(ValidationStatus.EXISTING_END_IS_PASSED_START, newInterval, existedInterval.id());
                }
            }

        } catch (BaseNotFoundException ignore) {}
        return new ValidationInfoDto(ValidationStatus.OK, null, null);
    }

    public ValidationInfoDto checkForOverlaps(UUID ownerId, Set<WorkIntervalMarker> newIntervalList) {
//        try {
//            List<SystemWorkIntervalDto> existingDtoList = service.systemFindAllByOwnerIdAndDate(ownerId, newInterval.date());
//
//            for (SystemWorkIntervalDto existedInterval: existingDtoList) {
//                // e |------|
//                // n |------|
//                if (existedInterval.equals(newInterval)) {
//                    return new ValidationInfoDto(ValidationStatus.ALREADY_EXISTING, null, null);
//                }
//                // e     |------|
//                // n |------|
//                if (existedInterval.start().isAfter(newInterval.start()) &&
//                        existedInterval.start().isBefore(newInterval.end())) {
//                    return new ValidationInfoDto(ValidationStatus.EXISTING_START_IS_IN_PASSED, newInterval, existedInterval.id());
//                }
//                // e |------|
//                // n     |------|
//                if (existedInterval.end().isAfter(newInterval.start()) &&
//                        existedInterval.end().isBefore(newInterval.end())) {
//                    return new ValidationInfoDto(ValidationStatus.EXISTING_END_IS_IN_PASSED, newInterval, existedInterval.id());
//                }
//                // e   |---|
//                // n |-------|
//                if (existedInterval.start().isAfter(newInterval.start()) &&
//                        existedInterval.end().isBefore(newInterval.end())) {
//                    return new ValidationInfoDto(ValidationStatus.EXISTING_INTERVAL_IS_INSIDE_PASSED, newInterval, existedInterval.id());
//                }
//                // e |-------|
//                // n   |---|
//                if (!existedInterval.start().isAfter(newInterval.start()) &&
//                        !existedInterval.end().isBefore(newInterval.end())) {
//                    return new ValidationInfoDto(ValidationStatus.PASSED_INTERVAL_IS_INSIDE_EXISTING, null, null);
//                }
//                // e        |------|
//                // n |------|
//                if (existedInterval.start().equals(newInterval.end())) {
//                    return new ValidationInfoDto(ValidationStatus.EXISTING_START_IS_PASSED_END, newInterval, existedInterval.id());
//                }
//                // e |------|
//                // n        |------|
//                if (existedInterval.end().equals(newInterval.start())) {
//                    return new ValidationInfoDto(ValidationStatus.EXISTING_END_IS_PASSED_START, newInterval, existedInterval.id());
//                }
//            }
//
//        } catch (BaseNotFoundException ignore) {}
        return new ValidationInfoDto(ValidationStatus.OK, null, null);
    }
}
