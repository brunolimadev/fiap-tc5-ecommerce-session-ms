package br.com.fiap.fiap_tc5_ecommerce_session_ms.repositories;

import org.springframework.stereotype.Repository;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.TokenModel;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface TokenRepository extends MongoRepository<TokenModel, String> {
}
