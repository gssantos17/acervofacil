package br.com.acervofacil.external.client.google;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

/**
 * Cliente responsável por consumir a API do Google Books.
 *
 * <p>Fornece métodos para buscar livros a partir de uma query textual
 * ou para buscar um livro específico por seu ID.</p>
 *
 * <p>Utiliza {@link RestTemplate} para realizar requisições HTTP.</p>
 */
@Component
public class GoogleBooksClient {

    private final RestTemplate restTemplate;
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * Construtor que injeta o {@link RestTemplate} usado para chamadas HTTP.
     *
     * @param restTemplate o cliente HTTP usado para comunicação com a API do Google Books
     */
    public GoogleBooksClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca livros na API do Google Books com base em uma consulta textual.
     *
     * @param query o termo de busca (ex: título, autor, ISBN, etc.)
     * @return uma resposta {@link GoogleBooksResponse} contendo os resultados encontrados
     */
    public GoogleBooksResponse buscarLivros(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_API_URL)
                .queryParam("q", query)
                .toUriString();
        return restTemplate.getForObject(url, GoogleBooksResponse.class);
    }

    /**
     * Busca os detalhes de um livro específico na API do Google Books utilizando seu ID.
     *
     * @param id o identificador único do livro na Google Books API
     * @return uma resposta {@link GoogleBooksResponse} com os dados do livro
     */
    public GoogleBooksResponse buscarLivroPorId(String id) {
        String url = GOOGLE_BOOKS_API_URL + "/" + id;
        return restTemplate.getForObject(url, GoogleBooksResponse.class);
    }
}
