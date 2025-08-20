package com.mdm.initialconfig.repository;

import com.mdm.initialconfig.model.auth.RefreshToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RefreshTokenRepository extends ReactiveCrudRepository<RefreshToken, UUID> {

    Mono<RefreshToken> findByToken(String token);
    Flux<RefreshToken> findByUserId(Long id);
    Mono<Void> deleteByUserId(Long userId);
}
