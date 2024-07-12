package br.com.fiap.fiap_tc5_ecommerce_session_ms.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.SessionModel;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.SessionRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.impl.SessionServiceImpl;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        sessionService = new SessionServiceImpl(sessionRepository, mongoTemplate);
    }

    @Test
    void devePermitirCriarUmaSessao() {

        CreateSessionRequestDto requestMock = new CreateSessionRequestDto("teste");

        when(sessionRepository.save(any(SessionModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = sessionService.createSession(requestMock);

        assertThat(response).isNotNull();
    }

    @Test
    void devePermitirBuscarUmaSessaoComSucesso() {

        SessionModel sessionModel = SessionModel.builder()
                .sessionId("teste")
                .userName("teste")
                .build();

        when(sessionRepository.findById(any(String.class))).thenReturn(Optional.of(sessionModel));

        var response = sessionService.getSession("teste");

        assertThat(response).isNotNull();
    }

    @Test
    void devePermitirBuscarUmaSessaoComErro() {

        when(sessionRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.getSession("teste"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Session not found");
    }

    @Test
    void devePermitirDeletarUmaSessao() {

        String sessionId = UUID.randomUUID().toString();

        doNothing().when(sessionRepository).deleteById(anyString());

        sessionService.deleteSession(sessionId);

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    void devePermitirDeletarSessoesExpiradas() {

        when(mongoTemplate.remove(any(), eq(SessionModel.class)))
                .thenReturn(DeleteResult.acknowledged(1));

        sessionService.deleteExpiredSessions();

        verify(mongoTemplate, times(1)).remove(any(Query.class), eq(SessionModel.class));
    }

}
