package com.nicolas.revenda.dto;

import com.nicolas.revenda.model.Role;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role,
        LocalDateTime criadoEm
) {
    public static UsuarioResponse from(com.nicolas.revenda.model.Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getCriadoEm()
        );
    }
}
