package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.enums.StatusMulta;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_multa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "O valor da multa não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor da multa deve ser maior que 0")
    @PositiveOrZero(message = "O valor da multa deve ser positivo ou zero")
    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status da multa não pode ser nulo")
    @Column(nullable = false)
    private StatusMulta status;

    @NotNull(message = "A data da multa não pode ser nula")
    @Column(name = "data_multa", nullable = false, updatable = false)
    private LocalDateTime dataMulta;

    @ManyToOne
    @JoinColumn(name = "id_emprestimo", referencedColumnName = "id", nullable = false)
    private Emprestimo emprestimo;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @NotNull(message = "A data de criação não pode ser nula")
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PreUpdate
    public void preUpdate(){
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PrePersist
    public  void prePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

}