package com.nicolas.revenda.service;

import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveRejeitarCpfDuplicado() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setCpf("111.111.111-11");

        when(clienteRepository.findByCpf("111.111.111-11"))
                .thenReturn(Optional.of(clienteExistente));

        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.criar("Nome Teste", "111.111.111-11", "(11) 99999-9999", "novo@teste.com", "São Paulo");
        });
    }

    @Test
    void deveRejeitarEmailDuplicado() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setEmail("existente@teste.com");

        when(clienteRepository.findByEmail("existente@teste.com"))
                .thenReturn(Optional.of(clienteExistente));

        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.criar("Nome Teste", "222.222.222-22", "(11) 98888-8888", "existente@teste.com", "São Paulo");
        });
    }
}