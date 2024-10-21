package aidcompass.api.security.models.token.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Token {

    private UUID id;
    private String subject;
    private List<String> authorities;
    private Instant issuedAt;
    private Instant expiresAt;

}