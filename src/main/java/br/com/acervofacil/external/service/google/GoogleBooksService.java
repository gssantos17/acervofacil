package br.com.acervofacil.external.service.google;

import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

/**
 * Serviço responsável por encapsular a lógica de busca de livros
 * na API do Google Books.
 *
 * <p>Fornece métodos para buscar livros por título, ISBN ou ID,
 * retornando dados brutos no formato {@link GoogleBooksResponse}.</p>
 *
 * <p>Esta interface visa abstrair a comunicação direta com a API externa,
 * permitindo uma implementação desacoplada e testável.</p>
 */
public interface GoogleBooksService {

    /**
     * Busca livros na API do Google Books com base no título informado.
     *
     * @param titulo o título do livro a ser pesquisado
     * @return um {@link GoogleBooksResponse} contendo os livros encontrados
     */
    GoogleBooksResponse buscarLivroPorTitulo(String titulo);

    /**
     * Busca livros na API do Google Books com base no número ISBN.
     *
     * @param isbn o código ISBN do livro
     * @return um {@link GoogleBooksResponse} contendo os livros correspondentes
     */
    GoogleBooksResponse buscarLivroPorIsbn(String isbn);

    /**
     * Busca um livro específico na API do Google Books utilizando seu ID.
     *
     * @param id o identificador único do livro no Google Books
     * @return um {@link GoogleBooksResponse} contendo os detalhes do livro
     */
    GoogleBooksResponse buscarLivroPorId(String id);
}