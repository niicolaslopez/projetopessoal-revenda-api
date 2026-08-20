package com.nicolas.revenda.dto;

import java.math.BigDecimal;

public record CriarVeiculoRequest(
        String marca,
        String modelo,
        int ano,
        BigDecimal preco,
        String descricao
) {
}
