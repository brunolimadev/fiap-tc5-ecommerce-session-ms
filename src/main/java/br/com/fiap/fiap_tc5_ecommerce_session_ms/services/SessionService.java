package br.com.fiap.fiap_tc5_ecommerce_session_ms.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.UpdateSessionDataRequestDto;

public interface SessionService {
    public CreateSessionResponseDto createSession(CreateSessionRequestDto userName);

    public GetSessionResponse getSession(String sessionId);

    public void updateSessionData(UpdateSessionDataRequestDto request)
            throws JsonProcessingException;

    public void deleteSession(String sessionId);

    public void deleteExpiredSessions();

}
