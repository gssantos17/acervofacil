package br.com.acervofacil.external.client.google;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

@Component
public class GoogleBooksClient {

    private final RestTemplate restTemplate;
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";

    public GoogleBooksClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleBooksResponse buscarLivros(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_API_URL)
                .queryParam("q", query)
                .toUriString();
        return restTemplate.getForObject(url, GoogleBooksResponse.class);
    }

    public GoogleBooksResponse buscarLivroPorId(String id) {
        String url = GOOGLE_BOOKS_API_URL + "/" + id;
        return restTemplate.getForObject(url, GoogleBooksResponse.class);
    }
}
