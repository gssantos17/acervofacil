package br.com.acervofacil.domain.service.livro;

import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Serviço responsável por realizar buscas de livros utilizando a API do Google Books.
 */
public interface LivroService {


    /**
     * Busca livros pelo título informado, utilizando a API do Google Books.
     *
     * @param titulo o título do livro a ser buscado (não pode ser nulo).
     * @return uma lista de objetos {@link LivroGoogleDTO} representando os livros encontrados.
     */
    List<LivroGoogleDTO> buscarLivroPeloTitulo(@NotNull String titulo);

    /**
     * Busca livros pelo ISBN informado, utilizando a API do Google Books.
     *
     * @param isbn o ISBN do livro a ser buscado (não pode ser nulo).
     * @return uma lista de objetos {@link LivroGoogleDTO} representando os livros encontrados.
     */
    LivroGoogleDTO buscarLivroPeloISBN(@NotNull String isbn);

    /**
     * Busca um livro específico pelo ID fornecido pela Google Books API.
     *
     * @param idGoogle o identificador único do livro na Google Books API (não pode ser nulo).
     * @return um objeto {@link LivroGoogleDTO} representando o livro encontrado.
     */
    LivroGoogleDTO buscarLivroPeloId(@NotNull String idGoogle);
}
