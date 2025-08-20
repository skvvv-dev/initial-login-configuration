package com.mdm.initialconfig.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 6, max = 20)
        String username,
        @NotBlank
        @Size(min = 9, max = 20)
        String password
) {}