package com.nicolas.revenda.repository;

import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByMarca(String marca);
    List<Veiculo> findByModelo(String modelo);
    List<Veiculo> findByStatus (StatusVeiculo status);
}
