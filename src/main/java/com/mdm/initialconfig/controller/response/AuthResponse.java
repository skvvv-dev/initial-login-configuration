package com.mdm.initialconfig.controller.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
