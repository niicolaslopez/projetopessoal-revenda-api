package com.nicolas.revenda.repository;


import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.model.StatusCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
    Page<Cliente> findByStatus(StatusCliente status, Pageable pageable);
}
