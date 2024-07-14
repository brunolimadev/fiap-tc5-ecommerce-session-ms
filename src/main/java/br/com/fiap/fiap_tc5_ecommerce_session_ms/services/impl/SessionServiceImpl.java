package br.com.fiap.fiap_tc5_ecommerce_session_ms.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.SessionModel;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.TokenModel;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.UpdateSessionDataRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.SessionRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.TokenRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.SessionService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final TokenRepository tokenRepository;

    private final MongoTemplate mongoTemplate;

    public SessionServiceImpl(SessionRepository sessionRepository, MongoTemplate mongoTemplate,
            TokenRepository tokenRepository) {
        this.sessionRepository = sessionRepository;
        this.mongoTemplate = mongoTemplate;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public CreateSessionResponseDto createSession(CreateSessionRequestDto request) {

        String sessionId = UUID.randomUUID().toString();

        SessionModel session = SessionModel.builder()
                .sessionId(sessionId)
                .userName(request.getUsername())
                .createAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(60))
                .token(request.getToken())
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

        try {
            SessionModel session = sessionRepository.findById(
                    request.getSessionId())
                    .orElseThrow(() -> new RuntimeException("Session not found"));

            if (session.getSessionData() == null) {
                Map<String, Object> data = new HashMap<>();
                data.put(request.getObjectKey(), request.getSessionData());
                session.setSessionData(data);
            } else {
                var data = session.getSessionData();
                data.put(request.getObjectKey(), request.getSessionData());
                session.setSessionData(data);
            }

            session.getSessionData().put(request.getObjectKey(), request.getSessionData());

            sessionRepository.save(session);
        } catch (Exception e) {
            throw new RuntimeException("Error updating session data");
        }

    }

    @Override
    public void deleteSession(String sessionId) {

        var response = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        tokenRepository.save(TokenModel.builder()
                .sessionId(sessionId)
                .token(response.getToken())
                .username(response.getUserName())
                .createdAt(LocalDateTime.now())
                .build());

        sessionRepository.deleteById(sessionId);
    }

    @Override
    public void deleteExpiredSessions() {

        Query query = new Query();
        query.addCriteria(Criteria.where("expiresAt").lt(LocalDateTime.now()));

        var result = mongoTemplate.remove(query, SessionModel.class);

        log.info("Deleted sessions: {}", result.getDeletedCount());
    }

}
