package com.mdm.initialconfig.model.auth;

import com.mdm.initialconfig.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String password;
    @Column("role")
    private String roleCode;
    @Transient
    private Role role;

    public Role getRole() {
        return Role.fromCode(this.roleCode);
    }
}
