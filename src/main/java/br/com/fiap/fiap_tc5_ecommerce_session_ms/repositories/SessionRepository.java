package br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.SessionModel;

@Repository
public interface SessionRepository extends MongoRepository<SessionModel, String> {
    public void deleteByExpiresAtBefore();
}
