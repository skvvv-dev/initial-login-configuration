package com.mdm.initialconfig.controller;

import com.mdm.initialconfig.controller.request.LoginRequest;
import com.mdm.initialconfig.controller.request.RefreshTokenRequest;
import com.mdm.initialconfig.controller.request.RegisterRequest;
import com.mdm.initialconfig.controller.response.AuthResponse;
import com.mdm.initialconfig.repository.UserRepository;
import com.mdm.initialconfig.service.auth.AuthService;
import com.mdm.initialconfig.util.SecurityContextUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@Valid @RequestBody RegisterRequest request) {
        return userRepository.findByUsername(request.username())
                .flatMap(existing -> Mono.just(ResponseEntity.badRequest().body("Usuario ya existe")))
                .switchIfEmpty(authService.register(request.username(), request.password())
                .thenReturn(ResponseEntity.ok("Usuario registrado"))
                );
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return authService.authenticate(request.username(), request.password())
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshAccessToken(request.refreshToken())
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<Void>> logout() {
        return SecurityContextUtils.getCurrentUsername()
                .flatMap(authService::logout)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
