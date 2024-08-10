package com.lucascoelho.auth_api.dto.auth;

public record RegisterRequestDTO(String username, String password, String firstName, String lastName, String email) {
}
