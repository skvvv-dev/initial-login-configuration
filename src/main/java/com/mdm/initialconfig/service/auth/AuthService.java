package com.mdm.initialconfig.service.auth;

import com.mdm.initialconfig.controller.response.AuthResponse;
import com.mdm.initialconfig.model.auth.User;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<User> register(String username, String password);
    Mono<AuthResponse> authenticate(String username, String rawPassword);
    Mono<AuthResponse> refreshAccessToken(String refreshToken);
    Mono<Void> logout(String username);

}
