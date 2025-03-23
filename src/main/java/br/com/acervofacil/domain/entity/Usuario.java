package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.enums.Role;
import br.com.acervofacil.domain.enums.StatusUsuario;
import br.com.acervofacil.domain.validation.CPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {

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

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(max = 16, message = "A senha não pode ter mais de 16 caracteres.")
    @Column(nullable = false, length = 16)
    private String senha;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status não pode ser nulo.")
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O role não pode ser nulo.")
    private Role role;

    @OneToOne(mappedBy = "usuario")
    private Cliente cliente;

    @OneToOne(mappedBy = "usuario")
    private Funcionario funcionario;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

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