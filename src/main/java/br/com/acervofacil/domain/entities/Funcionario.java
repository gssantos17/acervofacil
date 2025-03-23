package br.com.acervofacil.domain.entities;

import br.com.acervofacil.domain.enums.CargoFuncionario;
import br.com.acervofacil.domain.validation.CPF; // Importe a anotação customizada
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @CPF(message = "CPF inválido.") // Anotação customizada para validar CPF
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O Nome não pode estar vazio.")
    @Size(min = 5, max = 50, message = "O nome deve conter entre 5 e 50 caracteres.")
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

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

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true)
    private Usuario usuario;
}