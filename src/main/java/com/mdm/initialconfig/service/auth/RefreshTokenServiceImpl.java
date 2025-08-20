package com.mdm.initialconfig.service.auth;

import com.mdm.initialconfig.model.auth.RefreshToken;
import com.mdm.initialconfig.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Value("${security.jwt.refresh-token-expiration-days:7}")
    private long refreshTokenExpirationDays;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Mono<RefreshToken> createRefreshToken(Long userId) {
        var token = UUID.randomUUID().toString();
        var expiry = LocalDateTime.now().plusDays(refreshTokenExpirationDays);
        var refreshToken = RefreshToken.builder()
//                .id(UUID.randomUUID()) -> el UUID se esta generando por BD.
                .userId(userId)
                .token(token)
                .expiryDate(expiry)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Mono<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public Mono<Void> markAsUsed(RefreshToken token) {
        token.setUsed(true);
        return refreshTokenRepository.save(token).then();
    }

    @Override
    public Mono<Void> deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

}
