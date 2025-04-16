package br.com.acervofacil.api.utils;

import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;

import java.util.Optional;

public class ServiceUtils {

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
}
