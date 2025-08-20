package com.mdm.initialconfig.model.auth;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("refresh_tokens")
public class RefreshToken {

    @Id
    private UUID id;

    @Column("user_id")
    private Long userId;
    private String token;
    private boolean used = false;
    private LocalDateTime expiryDate;
}
