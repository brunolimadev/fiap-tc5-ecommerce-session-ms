package br.com.fiap.fiap_tc5_ecommerce_session_ms.configs.schedules;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.services.SessionService;

@Configuration
@EnableScheduling
public class CheckExpiredSessions {

    private final SessionService sessionService;

    public CheckExpiredSessions(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Check expired sessions every 60 seconds
     */
    @Scheduled(fixedRate = 60000, initialDelay = 4000)
    public void execute() {
        sessionService.deleteExpiredSessions();
    }

}
