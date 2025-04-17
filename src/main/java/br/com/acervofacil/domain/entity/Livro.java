package br.com.acervofacil.domain.entity;

import br.com.acervofacil.domain.enums.StatusLivro;
import br.com.acervofacil.domain.exception.ServiceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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

    @Column(name = "google_books_id", unique = true, length = 50)
    private String googleBooksId; // Ex: ID do Google Books para o livro

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo; // Título do livro

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();  // Relacionamento ManyToMany com autores

    @Column(name = "isbn", length = 20)
    private String isbn; // ISBN do livro

    @Column(name = "descricao", length = 500)
    private String descricao; // Descrição do livro

    @Column(name = "editora_nome", length = 255)
    private String editoraNome; // Nome da editora

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao; // Ano de publicação

    @Column(name = "idioma", length = 2)
    private String idioma; // Idioma do livro

    @Column(name = "numero_paginas")
    private Integer numeroPaginas; // Número de páginas

    @Column(name = "capa", length = 255)
    private String capa; // URL da capa do livro

    @Column(name = "genero", length = 255)
    private String genero; // Gênero do livro

    @Min(value = 0, message = "A quantidade disponível não pode ser negativa.")
    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel = 0; // Controle de quantidade disponível

    @Min(value = 0, message = "A quantidade total não pode ser negativa.")
    @Column(name = "quantidade_total")
    private Integer quantidadeTotal = 0; // Controle de quantidade total

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusLivro status = StatusLivro.DISPONIVEL; // Status do livro (Disponível, Emprestado, etc.)

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private Set<Emprestimo> emprestimos;

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

    public void diminuirQuantidadeDisponivel(Integer qtd){
        if( quantidadeDisponivel <= 0 )
            throw new ServiceException("Verifique as informações do livro, o mesmo não está disponivel no momento.");
        if( qtd <= 0)
            throw new ServiceException("Não é possível diminuir a quantidade, o valor do parametro está zerado.");
        this.quantidadeDisponivel -= qtd;
    }

    public void aumentarQuantidadeDisponivel(Integer qtd){
        this.quantidadeDisponivel += qtd;
    }
}