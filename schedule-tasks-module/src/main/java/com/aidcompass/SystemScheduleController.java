//package com.aidcompass;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//
//@RestController
//@RequestMapping("/api/system/v1/schedule")
//@RequiredArgsConstructor
//public class SystemScheduleController {
//
//
//    @DeleteMapping("/batch")
//    public ResponseEntity<?> deleteBatchByDate(@RequestParam("batchSize") int batchSize,
//                                               @RequestParam("date")LocalDate date) {
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .body(appointmentService.deleteBatchByDate(batchSize, date));
//    }
//}
