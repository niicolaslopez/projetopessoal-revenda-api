package com.nicolas.revenda.controller;


import com.nicolas.revenda.dto.CriarVeiculoRequest;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}