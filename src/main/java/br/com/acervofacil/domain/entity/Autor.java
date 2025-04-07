package br.com.acervofacil.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome do autor não pode estar vazio.")
    @Size(min = 2, max = 255, message = "O nome do autor deve ter entre 2 e 255 caracteres.")
    @Column(name = "nome", nullable = false, unique = true, length = 255)
    private String nome;

    @Size(max = 255, message = "A nacionalidade deve ter no máximo 255 caracteres.")
    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Past(message = "A data de nascimento deve estar no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}