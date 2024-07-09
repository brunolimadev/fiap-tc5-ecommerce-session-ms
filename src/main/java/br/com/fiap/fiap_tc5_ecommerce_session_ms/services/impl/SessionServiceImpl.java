package br.com.fiap.fiap_tc5_ecommerce_session_ms.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.SessionModel;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.UpdateSessionDataRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.SessionRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public CreateSessionResponseDto createSession(CreateSessionRequestDto request) {

        String sessionId = UUID.randomUUID().toString();

        SessionModel session = SessionModel.builder()
                .sessionId(sessionId)
                .userName(request.getUsername())
                .createAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(60))
                .sessionData(null)
                .build();

        sessionRepository.save(session);

        return new CreateSessionResponseDto(sessionId, session.getCreateAt());
    }

    @Override
    public GetSessionResponse getSession(String sessionId) {

        var response = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        GetSessionResponse responseDto = new GetSessionResponse();

        BeanUtils.copyProperties(response, responseDto);

        return responseDto;
    }

    @Override
    public void updateSessionData(UpdateSessionDataRequestDto request)
            throws JsonProcessingException {

        SessionModel session = sessionRepository.findById(
                request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));

        ObjectMapper objectMapper = new ObjectMapper();

        // Converte JSON strings para JsonNode
        JsonNode node1 = objectMapper.valueToTree(session.getSessionData());
        JsonNode node2 = objectMapper.valueToTree(request.getSessionData());

        // Cria um novo ObjectNode e mescla o JsonNode e o objeto JsonNode
        ObjectNode mergedObject = objectMapper.createObjectNode();
        mergedObject.setAll((ObjectNode) node1);
        mergedObject.setAll((ObjectNode) node2);

        session.setSessionData(mergedObject);

        sessionRepository.save(session);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    public void deleteExpiredSessions() {
        sessionRepository.deleteByExpiresAtBefore();
    }

}
