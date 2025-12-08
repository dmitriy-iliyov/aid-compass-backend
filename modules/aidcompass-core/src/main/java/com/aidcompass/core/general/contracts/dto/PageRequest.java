package com.aidcompass.core.general.contracts.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest {

    @PositiveOrZero(message = "Page should be positive!")
    protected Integer page;

    protected Integer size;

    public PageRequest(Integer page, Integer size) {
        this.page = Math.max(page, 0);
        this.size = size < 10 ? 10 : size;
    }
}
