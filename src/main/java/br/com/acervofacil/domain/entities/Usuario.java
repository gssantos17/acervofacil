package br.com.acervofacil.domain.entities;

import br.com.acervofacil.domain.enums.Role;
import br.com.acervofacil.domain.enums.StatusUsuario;
import br.com.acervofacil.domain.validation.CPF; // Importe a anotação customizada
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario {

    @Id
    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @CPF(message = "CPF inválido.")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(max = 16, message = "A senha não pode ter mais de 16 caracteres.")
    @Column(nullable = false, length = 16)
    private String senha;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_usuario DEFAULT 'ATIVO'")
    private StatusUsuario status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role DEFAULT 'USUARIO'")
    private Role role;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}