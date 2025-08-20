package com.mdm.initialconfig.service.auth;

import com.mdm.initialconfig.controller.response.AuthResponse;
import com.mdm.initialconfig.model.auth.User;
import com.mdm.initialconfig.repository.UserRepository;
import com.mdm.initialconfig.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<User> register(String username, String password) {
        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build());
    }

    public Mono<AuthResponse> authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
                        return Mono.error(new BadCredentialsException("Credenciales inválidas"));
                    }
                    String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
                    return refreshTokenService.createRefreshToken(user.getId())
                            .map(refreshToken -> new AuthResponse(
                                    accessToken,
                                    refreshToken.getToken()
                            ));
                })
                .switchIfEmpty(Mono.error(new BadCredentialsException("Usuario no encontrado")));
    }


    @Override
    public Mono<AuthResponse> refreshAccessToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .filter(token -> token.getExpiryDate().isAfter(LocalDateTime.now()))
                .switchIfEmpty(Mono.error(new RuntimeException("Refresh token inválido o expirado")))
                .flatMap(token -> userRepository.findById(token.getUserId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                        .flatMap(user -> {
                            String newAccessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

                            token.setUsed(true);
                            return refreshTokenService.markAsUsed(token).then(
                                    refreshTokenService.createRefreshToken(user.getId())
                                            .map(newToken -> new AuthResponse(newAccessToken, newToken.getToken()))
                            );
                        })
                );
    }

    @Override
    public Mono<Void> logout(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> refreshTokenService.deleteByUserId(user.getId()));
    }

}
