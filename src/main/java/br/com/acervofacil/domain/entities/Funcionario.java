package br.com.acervofacil.domain.entities;

import br.com.acervofacil.domain.enums.CargoFuncionario;
import br.com.acervofacil.domain.validation.CPF; // Importe a anotação customizada
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_funcionario")
public class Funcionario {

    @Id
    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @CPF(message = "CPF inválido.") // Anotação customizada para validar CPF
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "O cargo não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "cargo_funcionario DEFAULT 'BIBLIOTECARIO'")
    private CargoFuncionario cargo = CargoFuncionario.BIBLIOTECARIO;

    @OneToOne
    @JoinColumn(name = "contato_id", referencedColumnName = "id", unique = true)
    private Contato contato;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
    private Usuario usuario;
}