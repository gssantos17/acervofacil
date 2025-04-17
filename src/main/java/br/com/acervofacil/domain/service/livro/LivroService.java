package br.com.acervofacil.domain.service.livro;

import br.com.acervofacil.api.dto.request.RequisicaoLivroDTO;
import br.com.acervofacil.api.dto.response.LivroDTO;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas operações relacionadas a livros, tanto do sistema interno
 * quanto da API externa do Google Books.
 */
public interface LivroService {

    /**
     * Busca livros pelo título informado, utilizando a API do Google Books.
     *
     * @param titulo o título do livro a ser buscado (não pode ser nulo).
     * @return uma lista de objetos {@link LivroGoogleDTO} representando os livros encontrados.
     */
    List<LivroGoogleDTO> buscarLivroPeloTituloExterno(String titulo);

    /**
     * Salva um novo livro no sistema com base nos dados fornecidos.
     *
     * @param input o DTO contendo os dados do livro a ser cadastrado (não pode ser nulo).
     * @return o DTO {@link LivroDTO} representando o livro salvo.
     */
    LivroDTO salvar(RequisicaoLivroDTO input);

    /**
     * Busca livros pelo nome do autor, utilizando a API do Google Books.
     *
     * @param autor o nome do autor a ser buscado.
     * @return uma lista de objetos {@link LivroGoogleDTO} representando os livros encontrados.
     */
    List<LivroGoogleDTO> buscarLivroPeloAutor(String autor);

    /**
     * Busca um livro salvo no sistema pelo seu ID interno.
     *
     * @param id o identificador UUID do livro no sistema (não pode ser nulo).
     * @return o DTO {@link LivroDTO} representando o livro encontrado.
     */
    LivroDTO buscarLivroPeloId(UUID id);

    /**
     * Busca um livro salvo no sistema pelo seu ISBN.
     *
     * @param isbn o código ISBN do livro.
     * @return o DTO {@link LivroDTO} representando o livro encontrado.
     */
    LivroDTO buscarLivroPeloISBN(String isbn);

    /**
     * Retorna todos os livros cadastrados no sistema, paginados.
     *
     * @return uma página contendo objetos {@link LivroDTO}.
     */
    Page<LivroDTO> buscarLivroPeloTituloExterno();

    /**
     * Busca livros pelo ISBN informado, utilizando a API do Google Books.
     *
     * @param isbn o código ISBN do livro.
     * @return o DTO {@link LivroGoogleDTO} representando o livro encontrado.
     */
    LivroGoogleDTO buscarLivroPeloISBNExterno(String isbn);

    /**
     * Busca um livro específico utilizando o ID fornecido pela API do Google Books.
     *
     * @param idGoogle o identificador do livro na Google Books API (não pode ser nulo).
     * @return o DTO {@link LivroGoogleDTO} representando o livro encontrado.
     */
    LivroGoogleDTO buscarLivroPeloIdExterno(String idGoogle);
}