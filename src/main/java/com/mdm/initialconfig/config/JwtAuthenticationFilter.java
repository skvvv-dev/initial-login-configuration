package com.mdm.initialconfig.config;

import com.mdm.initialconfig.service.user.CustomReactiveUserDetailsService;
import com.mdm.initialconfig.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        System.out.println("FILTER STARTED");
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.getUsername(token);
                System.out.println("TOKEN VALID. USER: " + username);
                return userDetailsService.findByUsername(username)
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()))
                        .flatMap(auth -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));
            }
        }
        return chain.filter(exchange);
    }
}
