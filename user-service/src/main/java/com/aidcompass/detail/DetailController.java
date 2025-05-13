package com.aidcompass.detail;


import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/detail")
@RequiredArgsConstructor
public class DetailController {

    private final DetailService service;


//    @PostMapping("/{user_id}")
//    public ResponseEntity<?> createDetail(@PathVariable("user_id") UUID userId,
//                                          @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody,
//                                          @RequestBody @Valid DetailDto dto) {
//        PrivateDetailResponseDto response = service.save(userId, dto);
//        if (returnBody) {
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .body(response);
//        }
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }


    @PostMapping("/{user_id}")
    public ResponseEntity<?> createDetail(@PathVariable("user_id") UUID userId,
                                          @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody,
                                          @RequestBody @Valid DetailDto dto) {
        PrivateDetailResponseDto response = service.update(userId, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
