package com.nicolas.revenda.controller;

import com.nicolas.revenda.dto.AtualizarClienteRequest;
import com.nicolas.revenda.dto.ClienteResponse;
import com.nicolas.revenda.dto.CriarClienteRequest;
import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {


    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteResponse.from(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listar(Pageable pageable) {
        Page<Cliente> pagina = clienteService.listar(pageable);
        Page<ClienteResponse> paginaConvertida = pagina.map(ClienteResponse::from);
        return ResponseEntity.ok(paginaConvertida);
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

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarClienteRequest request) {
        Cliente novoCliente = clienteService.atualizar(
                id,
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email(),
                request.cidade()
        );
        return ResponseEntity.ok(ClienteResponse.from(novoCliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponse> arquivar(@PathVariable Long id) {
        Cliente cliente = clienteService.arquivar(id);
        return ResponseEntity.ok(ClienteResponse.from(cliente));
    }
}
