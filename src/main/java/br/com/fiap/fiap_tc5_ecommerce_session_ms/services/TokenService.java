package br.com.fiap.fiap_tc5_ecommerce_session_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions.RefusedTokenNotFounded;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetRevokedTokenResponseDto;

public interface TokenService {
    public GetRevokedTokenResponseDto getRevokedToken(String sessionId) throws RefusedTokenNotFounded;
}
