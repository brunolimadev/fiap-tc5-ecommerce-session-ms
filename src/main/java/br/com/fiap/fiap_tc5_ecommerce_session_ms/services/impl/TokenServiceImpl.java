package br.com.fiap.fiap_tc5_ecommerce_session_ms.services.impl;

import org.springframework.stereotype.Service;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions.RefusedTokenNotFounded;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.GetRevokedTokenResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories.TokenRepository;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public GetRevokedTokenResponseDto getRevokedToken(String sessionId) throws RefusedTokenNotFounded {
        var result = tokenRepository.findById(sessionId);

        if (result.isPresent()) {
            throw new RefusedTokenNotFounded("Token não encontrado");
        }

        return new GetRevokedTokenResponseDto(sessionId);
    }

}
