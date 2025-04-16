package br.com.acervofacil.external.service.google;

import br.com.acervofacil.external.client.google.GoogleBooksClient;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;
import org.springframework.stereotype.Service;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService {

    private final GoogleBooksClient googleBooksClient;

    public GoogleBooksServiceImpl(GoogleBooksClient googleBooksClient) {
        this.googleBooksClient = googleBooksClient;
    }

    @Override
    public GoogleBooksResponse buscarLivroPorTitulo(String titulo) {
        return googleBooksClient.buscarLivros("intitle:" + titulo);
    }

    @Override
    public GoogleBooksResponse buscarLivroPorAutor(String autor) {
        return googleBooksClient.buscarLivros("inauthor:" + autor);
    }

    @Override
    public GoogleBooksResponse buscarLivroPorIsbn(String isbn) {
        return googleBooksClient.buscarLivros("isbn:" + isbn);
    }

    @Override
    public GoogleBooksResponse buscarLivroPorId(String id) {
        return googleBooksClient.buscarLivroPorId(id);
    }
}
