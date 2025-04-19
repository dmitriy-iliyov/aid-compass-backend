package aidcompass.api.general.exeption.dto;

import java.util.LinkedList;
import java.util.Queue;

public record ExceptionResponseDto(String code,
                                   String message,
                                   String description) {
}
