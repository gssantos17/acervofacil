package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.enums.StatusLivro;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O título do livro não pode estar em branco.")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres.")
    @Column(name = "isbn", length = 20)
    private String isbn;

    @Past(message = "A data de publicação deve estar no passado.")
    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Size(max = 255, message = "O nome da editora deve ter no máximo 255 caracteres.")
    @Column(name = "editora_nome")
    private String editoraNome;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    @Size(max = 255, message = "O gênero deve ter no máximo 255 caracteres.")
    @Column(name = "genero")
    private String genero;

    @Min(value = 0, message = "A quantidade disponível não pode ser negativa.")
    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel = 0;

    @Min(value = 0, message = "A quantidade total não pode ser negativa.")
    @Column(name = "quantidade_total")
    private Integer quantidadeTotal = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusLivro status = StatusLivro.DISPONIVEL;

    @Min(value = 1, message = "O número de páginas deve ser maior que zero.")
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    @Column(name = "descricao")
    private String descricao;

    @Size(max = 255, message = "A URL da capa deve ter no máximo 255 caracteres.")
    @Column(name = "capa")
    private String capa;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Size(min = 2, max = 2, message = "O idioma deve conter 2 caracteres (ex: 'pt', 'en').")
    @Column(name = "idioma", length = 2)
    private String idioma;

    @Column(name = "open_library_id", unique = true, length = 50)
    private String openLibraryId; // Ex: OL123456W

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