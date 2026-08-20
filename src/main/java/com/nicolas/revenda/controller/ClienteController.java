package com.nicolas.revenda.controller;

import com.nicolas.revenda.dto.ClienteResponse;
import com.nicolas.revenda.dto.CriarClienteRequest;
import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {


    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody CriarClienteRequest request) {
        Cliente novoCliente = clienteService.criar(
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email(),
                request.cidade()
        );

        return ResponseEntity.ok(ClienteResponse.from(novoCliente));
    }
}
