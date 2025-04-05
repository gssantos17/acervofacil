package br.com.acervofacil.api.utils;

import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public abstract class ApiUtils {

    public static <T> ResponseEntity<RespostaPadronizada<T>> obterResponseEntityCreated(T object, String mensagem, URI uri) {
        RespostaPadronizada<T> resposta = mensagem == null ? RespostaPadronizada.created(object)
                : RespostaPadronizada.created(mensagem, object);
        return ResponseEntity.created(uri).body(resposta);
    }

    public static <T> ResponseEntity<RespostaPadronizada<T>> obterResponseEntityOk(T object, String mensagem) {
        RespostaPadronizada<T> resposta = mensagem == null ? RespostaPadronizada.ok(object)
                : RespostaPadronizada.ok(mensagem, object);
        return ResponseEntity.ok(resposta);
    }

    public static <T> ResponseEntity<RespostaPadronizada<T>> obterResponseEntityNoContent(String mensagem) {
        RespostaPadronizada<T> resposta = mensagem == null ? RespostaPadronizada.noContent()
                : RespostaPadronizada.noContent(mensagem);
        return ResponseEntity.status(204).body(resposta);
    }

    public static <T> ResponseEntity<RespostaPadronizada<T>> obterResponseEntityBadRequest(T object, String mensagem, String mensagemTecnica) {
        RespostaPadronizada<T> resposta = mensagem == null ? RespostaPadronizada.badRequest(object)
                : RespostaPadronizada.badRequest(mensagem, mensagemTecnica, object);
        return ResponseEntity.badRequest().body(resposta);
    }

    public static ResponseEntity<RespostaPadronizada<?>> obterResponseEntityNotFound(String mensagem) {
        RespostaPadronizada<?> resposta = mensagem == null ? RespostaPadronizada.notFound()
                : RespostaPadronizada.notFound(mensagem);
        return ResponseEntity.status(404).body(resposta);
    }

    public static <T> ResponseEntity<RespostaPadronizada<T>> obterResponseEntityInternalServerError(T object, String mensagem, String mensagemTecnica) {
        RespostaPadronizada<T> resposta = mensagem == null ? RespostaPadronizada.internalServerError(object)
                : RespostaPadronizada.internalServerError(mensagem,mensagemTecnica, object);
        return ResponseEntity.status(500).body(resposta);
    }

    public static URI getURI(String path, UUID uuid) {
        return UriComponentsBuilder.fromPath(path)
                .pathSegment(uuid.toString()) // Forma mais clara e segura
                .build()
                .toUri();
    }
}