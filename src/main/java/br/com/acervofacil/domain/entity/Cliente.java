package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.validation.CPF; // Importe a anotação customizada
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @CPF
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O Nome não pode estar vazio.")
    @Size(min = 5, max = 50, message = "O nome deve conter entre 5 e 50 caracteres.")
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contato_id", referencedColumnName = "id", unique = true)
    private Contato contato;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", unique = true)
    private Endereco endereco;

    @Column(name = "quantidade_emprestimos")
    private Integer quantidadeEmprestimos = 0;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Emprestimo> emprestimos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    private void prePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.dataAtualizacao = LocalDateTime.now();
    }
}