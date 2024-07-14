package br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions.RefusedTokenNotFounded;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.UpdateSessionDataRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.SessionService;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.TokenService;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    private final TokenService tokenService;

    public SessionController(SessionService sessionService, TokenService tokenService) {
        this.sessionService = sessionService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody CreateSessionRequestDto createSessionDto) {
        var response = sessionService.createSession(createSessionDto);
        return ResponseEntity.status(SC_CREATED).body(response);
    }

    @GetMapping("/revoked-tokens/{sessionId}")
    public ResponseEntity<?> getRevokedToken(@PathVariable String sessionId) throws RefusedTokenNotFounded {
        return ResponseEntity.ok(tokenService.getRevokedToken(sessionId));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        return ResponseEntity.ok(sessionService.getSession(sessionId));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable String sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateSessionData(@RequestBody UpdateSessionDataRequestDto updateSessionDataDto)
            throws JsonProcessingException {
        sessionService.updateSessionData(updateSessionDataDto);
        return ResponseEntity.noContent().build();
    }

}
