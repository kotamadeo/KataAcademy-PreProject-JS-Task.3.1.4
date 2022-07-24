package com.gmail.at.kotamadeo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "Роль не может быть пустой")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "Доступные роли: ADMIN\\USER!")
    private String roleTitle;

    public Role(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    @Override
    public String getAuthority() {
        return getRoleTitle();
    }
}
