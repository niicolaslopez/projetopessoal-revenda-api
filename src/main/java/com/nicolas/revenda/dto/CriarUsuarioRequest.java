package com.nicolas.revenda.dto;

import com.nicolas.revenda.model.Role;

public record CriarUsuarioRequest(
        String nome,
        String email,
        String senha,
        Role role
) {
}
