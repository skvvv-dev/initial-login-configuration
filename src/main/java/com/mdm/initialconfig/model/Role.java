package com.mdm.initialconfig.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    USER("U"),
    ADMIN("A");

    private final String code;

    Role(String code) {
        this.code = code;
    }

    public static Role fromCode(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role code: " + code));
    }
}
