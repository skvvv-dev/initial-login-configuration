package com.mdm.initialconfig.service.user;

import com.mdm.initialconfig.model.Role;
import com.mdm.initialconfig.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
//                    String springRole = user.getRole().name().replace("ROLE_", "");
                    String springRole = Role.fromCode(user.getRoleCode()).name();
                    return User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .roles(springRole)
                            .build();
                });
    }
}
