package com.nicolas.revenda.dto;

import java.math.BigDecimal;

public record AtualizarVeiculoRequest(
        String marca,
        String modelo,
        int ano,
        BigDecimal preco,
        String descricao
) {
}
