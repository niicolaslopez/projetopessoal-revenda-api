package com.nicolas.revenda.controller;


import com.nicolas.revenda.dto.AtualizarVeiculoRequest;
import com.nicolas.revenda.dto.CriarVeiculoRequest;
import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Veiculo> criar(@RequestBody CriarVeiculoRequest request){
        Veiculo novoVeiculo = veiculoService.criar(
                request.marca(),
                request.modelo(),
                request.ano(),
                request.preco(),
                request.descricao()
        );

        return ResponseEntity.ok(novoVeiculo);
    }

    @GetMapping
    public ResponseEntity<Page<Veiculo>> listar(Pageable pageable) {
        Page<Veiculo> pagina = veiculoService.listar(pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/marca")
    public ResponseEntity<Page<Veiculo>> listarPorMarca(@RequestParam String marca, Pageable pageable) {
        Page<Veiculo> paginaMarca = veiculoService.listarPorMarca(marca, pageable);
        return ResponseEntity.ok(paginaMarca);
    }

    @GetMapping("/modelo")
    public ResponseEntity<Page<Veiculo>> listarPorModelo(@RequestParam String modelo, Pageable pageable) {
        Page<Veiculo> paginaModelo = veiculoService.listarPorModelo(modelo, pageable);
        return ResponseEntity.ok(paginaModelo);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<Veiculo>> listarPorStatus(@RequestParam StatusVeiculo status, Pageable pageable) {
        Page<Veiculo> paginaStatus = veiculoService.listarPorStatus(status, pageable);
        return ResponseEntity.ok(paginaStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        Veiculo veiculo = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(veiculo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Veiculo> arquivar(@PathVariable Long id) {
        Veiculo veiculo = veiculoService.arquivar(id);
        return ResponseEntity.ok(veiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody AtualizarVeiculoRequest request) {
        Veiculo novoVeiculo = veiculoService.atualizar(
                id,
                request.marca(),
                request.modelo(),
                request.ano(),
                request.preco(),
                request.descricao()
        );
        return ResponseEntity.ok(novoVeiculo);
    }

}