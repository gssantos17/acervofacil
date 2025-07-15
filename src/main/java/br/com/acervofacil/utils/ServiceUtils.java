package br.com.acervofacil.utils;

import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;


import java.util.Optional;

public class ServiceUtils {

    private static final int TAMANHO_PADRAO = 15;
    private static final Sort.Direction DIRECAO_PADRAO = Sort.Direction.DESC;

    public static <T> T obterOuLancar(Optional<T> optional, String descricao, String parametro) {
        return optional.orElseThrow(() ->
                new NaoEncontradoException(String.format(
                        "%s não encontrado. Parâmetro usado: %s", descricao, parametro
                ))
        );
    }

    public static void seNaoExisteLancaExterno(GoogleBooksResponse googleBooksResponse, String parametro){
        if( !googleBooksResponse.existeItem() )
            throw new NaoEncontradoException("Não existe dados para retornar, parametro informado: " + parametro);
    }

    public static Pageable criarPageable(int pagina, Integer tamanho, String campoOrdenacao, String direcao) {
        int tamanhoFinal = (tamanho != null && tamanho > 0) ? tamanho : TAMANHO_PADRAO;
        Sort.Direction direcaoFinal = parseDirecao(direcao);

        if (campoOrdenacao != null && !campoOrdenacao.isBlank()) {
            return PageRequest.of(pagina, tamanhoFinal, Sort.by(direcaoFinal, campoOrdenacao));
        } else {
            return PageRequest.of(pagina, tamanhoFinal);
        }
    }

    private static Sort.Direction parseDirecao(String direcao) {
        if (direcao == null || direcao.isBlank()) {
            return DIRECAO_PADRAO;
        }

        try {
            return Sort.Direction.valueOf(direcao.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DIRECAO_PADRAO;
        }
    }
}
