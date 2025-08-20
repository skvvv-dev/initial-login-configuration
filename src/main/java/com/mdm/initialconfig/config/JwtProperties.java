package com.mdm.initialconfig.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String secret;
    private Long accessTokenExpirationMinutes;
    private Long refreshTokenExpirationDays;
}
