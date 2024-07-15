package br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetRevokedTokenResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.SessionService;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.TokenService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SessionService sessionService;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SessionController(sessionService, tokenService)).build();
    }

    @Test
    void devePermitirCriarUmaSessao() throws Exception {
        CreateSessionRequestDto requestMock = new CreateSessionRequestDto("teste", "token");

        when(sessionService.createSession(any(CreateSessionRequestDto.class))).thenReturn(
                new CreateSessionResponseDto(UUID.randomUUID().toString(), LocalDateTime.now()));

        mockMvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestMock)))
                .andExpect(status().isCreated());

        verify(sessionService, times(1)).createSession(any(CreateSessionRequestDto.class));
    }

    @Test
    void devePermitirBuscarUmaSessaoComSucesso() throws Exception {

        GetSessionResponse response = new GetSessionResponse();
        response.setSessionId("teste");
        response.setUserName("teste");

        when(sessionService.getSession(anyString())).thenReturn(response);

        mockMvc.perform(get("/sessions/teste"))
                .andExpect(status().isOk());

        verify(sessionService, times(1)).getSession(any(String.class));
    }

    @Test
    void devePermitirDeletarUmaSessaoComSucesso() throws Exception {

        GetSessionResponse response = new GetSessionResponse();
        response.setSessionId("teste");
        response.setUserName("teste");

        doNothing().when(sessionService).deleteSession(anyString());

        mockMvc.perform(delete("/sessions/teste"))
                .andExpect(status().is2xxSuccessful());

        verify(sessionService, times(1)).deleteSession(any(String.class));
    }

    @Test
    void devePermitirBuscarUmTokenRevogadoComSucesso() throws Exception {

        GetRevokedTokenResponseDto response = new GetRevokedTokenResponseDto();
        response.setSessionId("teste");

        when(tokenService.getRevokedToken(anyString())).thenReturn(response);

        mockMvc.perform(get("/sessions/revoked-tokens/teste"))
                .andExpect(status().isOk());

        verify(tokenService, times(1)).getRevokedToken(any(String.class));
    }

}
