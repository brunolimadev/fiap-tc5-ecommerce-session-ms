package br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSessionResponse {
    private String sessionId;

    private String userName;

    private LocalDateTime createAt;

    private LocalDateTime expiresAt;

    private Object sessionData;
}
