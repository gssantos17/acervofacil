package br.com.acervofacil.domain.entities;

import br.com.acervofacil.domain.validation.CPF; // Importe a anotação customizada
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @CPF(message = "CPF inválido.")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "contato_id", referencedColumnName = "id", unique = true)
    private Contato contato;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", unique = true)
    private Endereco endereco;

    @Column(name = "quantidade_emprestimos", columnDefinition = "INT DEFAULT 0")
    private Integer quantidadeEmprestimos = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    private Usuario usuario;
}