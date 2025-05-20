package com.aidcompass.doctor.models.dto.page;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.RequestParam;

public record PageDto(
        @RequestParam(value = "page", defaultValue = "0")
        @PositiveOrZero(message = "Page should be positive!")
        int page,

        @RequestParam(value = "size", defaultValue = "10")
        @Positive(message = "Size should be positive!")
        int size) {

        public PageDto {
               if (size < 10) {
                       size = 10;
               }
        }
}
