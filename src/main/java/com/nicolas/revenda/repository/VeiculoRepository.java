package com.nicolas.revenda.repository;

import com.nicolas.revenda.model.StatusVeiculo;
import com.nicolas.revenda.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByMarca(String marca);
    List<Veiculo> findByModelo(String modelo);
    List<Veiculo> findByStatus (StatusVeiculo status);

    Page<Veiculo> findByStatus(StatusVeiculo status, Pageable pageable);
    Page<Veiculo> findByMarca(String marca, Pageable pageable);
    Page<Veiculo> findByModelo(String modelo, Pageable pageable);
}
