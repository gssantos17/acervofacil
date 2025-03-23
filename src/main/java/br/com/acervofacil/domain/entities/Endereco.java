package br.com.acervofacil.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Size(max = 5, message = "O número não pode ter precisão  maior que 5.")
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

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}