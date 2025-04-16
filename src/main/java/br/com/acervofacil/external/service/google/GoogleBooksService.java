package br.com.acervofacil.external.service.google;

import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

public interface GoogleBooksService {

    GoogleBooksResponse buscarLivroPorTitulo(String titulo);

    GoogleBooksResponse buscarLivroPorIsbn(String isbn);

    GoogleBooksResponse buscarLivroPorId(String id);
}
