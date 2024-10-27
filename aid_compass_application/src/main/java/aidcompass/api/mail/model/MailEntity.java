package aidcompass.api.mail.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="mail_confirmation")
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false, unique = true, length = 50)
    private String mail;

    @Column(name = "confirmation_key", nullable = false, length = 6)
    private String key;

    public MailEntity(String mail, String key){
        this.mail = mail;
        this.key = key;
    }
}
