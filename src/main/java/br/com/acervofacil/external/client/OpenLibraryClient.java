package br.com.acervofacil.external.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenLibraryClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://openlibrary.org/search.json";

    public OpenLibraryClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String buscarPorTitulo(String titulo) {
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("title", titulo)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    public String buscarPorAutor(String autor) {
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("author", autor)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    public String buscarPorTituloEAutor(String titulo, String autor) {
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("title", titulo)
                .queryParam("author", autor)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
