package com.nicolas.revenda.service;

import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @Test
    void deveCriarVeiculoComStatusDisponivel() {
        Veiculo veiculoSalvo = new Veiculo();
        veiculoSalvo.setId(1L);
        veiculoSalvo.setStatus(StatusVeiculo.DISPONIVEL);

        when(veiculoRepository.save(org.mockito.ArgumentMatchers.any(Veiculo.class)))
                .thenReturn(veiculoSalvo);

        Veiculo resultado = veiculoService.criar("Toyota", "Corolla", 2022, new BigDecimal("120000.00"), "desc" );

        assertEquals(StatusVeiculo.DISPONIVEL, resultado.getStatus());
    }
}