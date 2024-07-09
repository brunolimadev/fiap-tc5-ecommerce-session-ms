package br.com.fiap.fiap_tc5_ecommerce_session_ms.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "sessions")
@Getter
@Setter
@Builder
public class SessionModel {

    @Id
    private String sessionId;

    private String userName;

    private LocalDateTime createAt;

    private LocalDateTime expiresAt;

    private Object sessionData;
}
