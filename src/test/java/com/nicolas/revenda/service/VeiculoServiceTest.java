package com.nicolas.revenda.service;

import com.nicolas.revenda.exception.RecursoNaoEncontradoException;
import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoExiste() {
        when(veiculoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            veiculoService.buscarPorId(999L);
        });
    }

    @Test
    void deveArquivarVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(2L);
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        when(veiculoRepository.findById(2L)).thenReturn(java.util.Optional.of(veiculo));
        when(veiculoRepository.save(org.mockito.ArgumentMatchers.any(Veiculo.class)))
        .thenReturn(veiculo);
        Veiculo resultado = veiculoService.arquivar (2L);

        assertEquals(StatusVeiculo.ARQUIVADO, resultado.getStatus());
    }
}