package com.nicolas.revenda.service;


import com.nicolas.revenda.exception.RecursoNaoEncontradoException;
import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.repository.VeiculoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo criar(String marca, String modelo, int ano, BigDecimal preco, String descricao) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setPreco(preco);
        veiculo.setDescricao(descricao);
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        return veiculoRepository.save(veiculo);
    }

    public Page<Veiculo> listar(Pageable pageable) {
        return veiculoRepository.findAll(pageable);

    }

    public Page<Veiculo> listarPorMarca(String marca, Pageable pageable) {
        return veiculoRepository.findByMarca(marca, pageable);
    }

    public Page<Veiculo> listarPorModelo(String modelo, Pageable pageable) {
        return veiculoRepository.findByModelo(modelo, pageable);
    }

    public Page<Veiculo> listarPorStatus(StatusVeiculo status, Pageable pageable) {
        return veiculoRepository.findByStatus(status, pageable);
    }

    public Veiculo buscarPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo não encontrado"));
        return veiculo;
    }

    public Veiculo arquivar(Long id) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setStatus(StatusVeiculo.ARQUIVADO);
        return veiculoRepository.save(veiculo);
    }
}

