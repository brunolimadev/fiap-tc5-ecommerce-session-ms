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
public class CreateSessionResponseDto {
    private String sessionId;
    private LocalDateTime createAt;
}