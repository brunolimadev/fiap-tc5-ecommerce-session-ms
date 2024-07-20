package br.com.fiap.fiap_tc5_ecommerce_session_ms.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions.RefusedTokenNotFounded;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.TokenModel;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.TokenRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.impl.TokenServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    private TokenServiceImpl tokenService;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenServiceImpl(tokenRepository);
    }

    @Test
    void deveRetornarUmTokenRevogado() throws RefusedTokenNotFounded {

        Optional<TokenModel> tokenModel = Optional.of(TokenModel.builder().sessionId("sessionId").username("username")
                .token("token").createdAt(LocalDateTime.now()).build());

        when(tokenRepository.findById(anyString())).thenReturn(tokenModel);

        assertThrows(RefusedTokenNotFounded.class, () -> {
            tokenService.getRevokedToken("sessionId");
        });

        verify(tokenRepository, times(1)).findById("sessionId");
    }

}
