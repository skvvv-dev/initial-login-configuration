package com.mdm.initialconfig.util;


import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class SecurityContextUtils {

    public static Mono<String> getCurrentUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(ctx -> System.out.println("Security context retrieved"))
                .map(ctx -> ctx.getAuthentication().getPrincipal())
                .cast(UserDetails.class)
                .map(UserDetails::getUsername);
    }
}
