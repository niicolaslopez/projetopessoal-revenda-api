package com.nicolas.revenda.dto;

public record AtualizarClienteRequest(
        String nome,
        String cpf,
        String telefone,
        String email,
        String cidade
) {

}
