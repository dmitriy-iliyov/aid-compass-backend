package com.aidcompass.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{userId}")
    public ResponseEntity<?> createAvatar(@PathVariable("userId") UUID userId,
                                          @RequestParam("image") MultipartFile image) {
        avatarService.create(userId, image);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{userId}")
    public ResponseEntity<?> updateAvatar(@PathVariable("userId") UUID userId,
                                          @RequestParam("image") MultipartFile image) {
        avatarService.create(userId, image);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(value = "/bytes/{userId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getAvatarBytes(@PathVariable("userId") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.findBytesByUserId(userId));
    }

    @GetMapping(value = "/base64/{userId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAvatarBase64(@PathVariable("userId") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.findBase64ByUserId(userId));
    }

    @GetMapping( "/url/{userId}")
    public ResponseEntity<String> getAvatarUrl(@PathVariable("userId") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.findUrlByUserId(userId));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteAvatar(@PathVariable("userId") UUID userId) {
        avatarService.deleteByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
