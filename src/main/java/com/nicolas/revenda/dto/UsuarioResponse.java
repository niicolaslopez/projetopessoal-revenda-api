package com.nicolas.revenda.dto;

import com.nicolas.revenda.model.Role;
import com.nicolas.revenda.model.StatusUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role,
        StatusUsuario status,
        LocalDateTime criadoEm
) {
    public static UsuarioResponse from(com.nicolas.revenda.model.Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getStatus(),
                usuario.getCriadoEm()
        );
    }
}
