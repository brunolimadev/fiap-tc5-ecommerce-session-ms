package br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequestDto {
    private String username;
    private String token;
}
