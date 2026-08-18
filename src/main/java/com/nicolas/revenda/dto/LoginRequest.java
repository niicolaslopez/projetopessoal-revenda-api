package com.nicolas.revenda.dto;

public record LoginRequest(
        String email,
        String senha
) {
}
