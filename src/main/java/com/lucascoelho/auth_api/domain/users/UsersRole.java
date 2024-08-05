package com.lucascoelho.auth_api.domain.users;

import lombok.Getter;

@Getter
public enum UsersRole {
    MEMBER("MEMBER"), ADMIN("ADMIN");

    private final String role;

    UsersRole(String role) {
        this.role = role.toUpperCase();
    }
}
