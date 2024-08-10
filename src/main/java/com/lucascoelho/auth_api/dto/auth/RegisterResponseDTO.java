package com.lucascoelho.auth_api.dto.auth;

import java.util.UUID;

public record RegisterResponseDTO(UUID id, String username, String token) {
}
