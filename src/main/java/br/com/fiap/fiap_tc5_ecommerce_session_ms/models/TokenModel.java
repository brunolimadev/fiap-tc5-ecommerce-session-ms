package br.com.fiap.fiap_tc5_ecommerce_session_ms.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "revoked_tokens")
@Getter
@Setter
@Builder
public class TokenModel {

    @Id
    private String sessionId;
    private String username;
    private String token;
    private LocalDateTime createdAt;
}
