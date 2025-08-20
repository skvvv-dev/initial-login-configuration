package com.mdm.initialconfig.service.auth;

import com.mdm.initialconfig.model.auth.RefreshToken;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {

    Mono<RefreshToken> createRefreshToken(Long userId);
    Mono<RefreshToken> findByToken(String token);
    Mono<Void> markAsUsed(RefreshToken token);
    Mono<Void> deleteByUserId(Long userId);
}
