package br.com.acervofacil.external.service;

import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.external.client.OpenLibraryClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OpenLibraryService {

    private final OpenLibraryClient client;

    public OpenLibraryService(OpenLibraryClient client) {
        this.client = client;
    }

    public String buscarLivros(String titulo, String autor) {
        boolean temTitulo = StringUtils.hasText(titulo);
        boolean temAutor = StringUtils.hasText(autor);

        if (temTitulo && temAutor) {
            return client.buscarPorTituloEAutor(titulo, autor);
        } else if (temTitulo) {
            return client.buscarPorTitulo(titulo);
        } else if (temAutor) {
            return client.buscarPorAutor(autor);
        } else {
            throw new ServiceException("É necessário informar ao menos o título ou o autor.");
        }
    }
}
