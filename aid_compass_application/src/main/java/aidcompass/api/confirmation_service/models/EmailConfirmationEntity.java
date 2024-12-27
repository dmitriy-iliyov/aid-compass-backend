package aidcompass.api.confirmation_service.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "email_confirmation_tokens")
public class EmailConfirmationEntity {

    @Id
    @SequenceGenerator(name = "email_token_sequence", sequenceName = "email_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_sequence")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token", nullable = false)
    private UUID token;

    @Column(name = "expiresAt", nullable = false)
    private Instant expiresAt;

    public EmailConfirmationEntity(String email, UUID token, Instant expiresAt){
        this.email = email;
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
