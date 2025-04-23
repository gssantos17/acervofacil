package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.enums.StatusEmprestimo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "emprestimo")
@Data
@NoArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "taxa_emprestimo", precision = 10, scale = 2)
    @PositiveOrZero(message = "A taxa de empréstimo não pode ser negativa.")
    private BigDecimal taxaEmprestimo;

    @Column(name = "data_emprestimo", updatable = false)
    @NotNull(message = "A data do empréstimo não pode ser nula.")
    private LocalDateTime dataEmprestimo;

    @Column(name = "data_devolucao_prevista")
    //@FutureOrPresent(message = "A data de devolução prevista deve ser no futuro ou na data atual.")
    private LocalDateTime dataDevolucaoPrevista;

    @Column(name = "data_devolucao_real")
    @PastOrPresent(message = "A data de devolução real não pode ser no futuro.")
    private LocalDateTime dataDevolucaoReal;

    @Column(name = "quantidade_emprestada")
    @NotNull(message = "A quantidade emprestada não pode ser nula.")
    @Min(value = 1, message = "A quantidade emprestada deve ser no mínimo 1.")
    private Integer quantidadeEmprestada;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "O status do empréstimo não pode ser nulo.")
    private StatusEmprestimo status;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_responsavel", referencedColumnName = "id", nullable = false)
    private Funcionario funcionarioResponsavel;

    @ManyToOne
    @JoinColumn(name = "livro_id", referencedColumnName = "id", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @OneToOne(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Multa multa;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist(){
        this.dataCriacao    = LocalDateTime.now();
        this.dataEmprestimo = LocalDateTime.now();
        this.status         = StatusEmprestimo.ATIVO;
    }

    @PreUpdate
    public void preUpdate(){
        this.dataAtualizacao = LocalDateTime.now();
    }
}