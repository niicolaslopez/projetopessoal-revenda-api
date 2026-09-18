package com.nicolas.revenda.dto;

import com.nicolas.revenda.model.StatusCliente;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String cidade,
        StatusCliente status,
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
                cliente.getStatus(),
                cliente.getCriadoEm()
        );
    }
}