package com.mdm.initialconfig.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        String username,
        String password
) {}