package br.com.acervofacil.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "A rua não pode estar vazia.")
    @Size(max = 255, message = "A rua não pode ter mais de 255 caracteres.")
    @Column(nullable = false, length = 255)
    private String rua;

    @Positive
    @Digits(integer = 5, fraction = 0)
    @Column(length = 5)
    private int numero;

    @Size(max = 255, message = "O complemento não pode ter mais de 255 caracteres.")
    @Column(length = 255)
    private String complemento;

    @Size(max = 255, message = "O bairro não pode ter mais de 255 caracteres.")
    @Column(length = 255)
    private String bairro;

    @NotBlank(message = "A cidade não pode estar vazia.")
    @Size(max = 255, message = "A cidade não pode ter mais de 255 caracteres.")
    @Column(nullable = false, length = 255)
    private String cidade;

    @NotBlank(message = "O estado não pode estar vazio.")
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres.")
    @Column(nullable = false, length = 2)
    private String estado;

    @NotBlank(message = "O CEP não pode estar vazio.")
    @Pattern(regexp = "^[0-9]{8}$",
            message = "O CEP deve conter exatamente 8 dígitos numéricos.")
    @Column(length = 8)
    private String cep;

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