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
@Table(name = "tb_contato")
public class Contato {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O telefone celular não pode estar vazio.")
    @Pattern(regexp = "^[0-9]{11}$",
            message = "O telefone celular deve conter 11 dígitos numéricos.")
    @Column(name = "telefone_celular", nullable = false, length = 11)
    private String telefoneCelular;

    @NotBlank(message = "O telefone fixo não pode estar vazio.")
    @Pattern(regexp = "^[0-9]{10}$",
            message = "O telefone fixo deve conter 10 dígitos numéricos.")
    @Column(name = "telefone_fixo", nullable = false, length = 10)
    private String telefoneFixo;

    @NotBlank(message = "O email não pode estar vazio.")
    @jakarta.validation.constraints.Email(message = "O email deve ser válido.")
    @Size(max = 255, message = "O email não pode ter mais de 255 caracteres.")
    @Column(nullable = false, length = 255)
    private String email;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}