package com.nicolas.revenda.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String cidade,
        LocalDateTime criadoEm
) {
    public static ClienteResponse from(com.nicolas.revenda.model.Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCidade(),
                cliente.getCriadoEm()
        );
    }
}