package com.nicolas.revenda.service;


import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import com.nicolas.revenda.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

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


}
