package com.nicolas.revenda.dto;

public record CriarClienteRequest(
        String nome,
        String cpf,
        String telefone,
        String email,
        String cidade
) {
}
