package br.com.acervofacil.external.client.google;

import br.com.acervofacil.external.dto.google.GoogleBookVolume;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

import java.util.List;

/**
 * Cliente responsável por consumir 'a' API do Google Books.
 *
 * <p>Fornece métodos para buscar livros a partir de uma query textual
 * ou para buscar um livro específico por seu ‘ID’.</p>
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
     * Busca livros na API do Google Books com base numa consulta textual.
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
     * Busca os detalhes de um livro na API do Google Books utilizando o seu ID único.
     * <p>
     * Esse método faz uma requisição ao endpoint {@code https://www.googleapis.com/books/v1/volumes/{id}},
     * que retorna os dados de um único volume. Como a resposta não está no formato padrão de busca múltipla
     * (com {@code totalItems} e {@code items}), este método adapta o resultado encapsulando o volume retornado
     * em uma instância de {@link GoogleBooksResponse}.
     * </p>
     *
     * @param id o identificador único do livro na Google Books API
     * @return um {@link GoogleBooksResponse} com um único ‘item’ representando o livro encontrado,
     *         ou uma instância vazia caso a resposta da API seja {@code null}
     */
    public GoogleBooksResponse buscarLivroPorId(String id) {
        String url = GOOGLE_BOOKS_API_URL + "/" + id;
        GoogleBookVolume volume = restTemplate.getForObject(url, GoogleBookVolume.class);

        if (volume == null) return new GoogleBooksResponse();

        GoogleBooksResponse.Item item = new GoogleBooksResponse.Item();
        item.setId(volume.getId());
        item.setVolumeInfo(volume.getVolumeInfo());

        GoogleBooksResponse response = new GoogleBooksResponse();
        response.setTotalItems(1);
        response.setItems(List.of(item));

        return response;
    }

}
