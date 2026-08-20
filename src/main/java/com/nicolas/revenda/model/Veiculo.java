package com.nicolas.revenda.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor

public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 21)
    private String marca;

    @Column(nullable = false, length = 21)
    private String modelo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false, length = 21)
    private StatusVeiculo status;

    @Column(nullable = true, length = 250)
    private String descricao;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }


}
