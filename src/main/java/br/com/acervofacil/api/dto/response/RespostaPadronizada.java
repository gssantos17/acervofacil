package br.com.acervofacil.api.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class RespostaPadronizada<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final String mensagem;
    private final String mensagemTecnica;
    private final T dados;

    private RespostaPadronizada(HttpStatus status, String mensagem, String mensagemTecnica, T dados) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.mensagem = mensagem;
        this.mensagemTecnica = mensagemTecnica;
        this.dados = dados;
    }

    // ✅ 200 - OK
    public static <T> RespostaPadronizada<T> ok(String mensagem, T dados) {
        return new RespostaPadronizada<>(HttpStatus.OK, mensagem, null, dados);
    }

    public static <T> RespostaPadronizada<T> ok(T dados) {
        return new RespostaPadronizada<>(HttpStatus.OK, "Operação realizada com sucesso.",null, dados);
    }

    // ✅ 201 - Created
    public static <T> RespostaPadronizada<T> created(String mensagem, T dados) {
        return new RespostaPadronizada<>(HttpStatus.CREATED, mensagem, null,dados);
    }

    public static <T> RespostaPadronizada<T> created(T dados) {
        return new RespostaPadronizada<>(HttpStatus.CREATED, "Recurso criado com sucesso.",null, dados);
    }

    // ✅ 204 - No Content
    public static <T> RespostaPadronizada<T> noContent(String mensagem) {
        return new RespostaPadronizada<>(HttpStatus.NO_CONTENT, mensagem, null,null);
    }

    public static <T> RespostaPadronizada<T> noContent() {
        return new RespostaPadronizada<>(HttpStatus.NO_CONTENT, "Nenhum conteúdo disponível.", null, null);
    }

    // 400 - Bad Request
    public static <T> RespostaPadronizada<T> badRequest(String mensagem, String mensagemTecnica, T dados) {
        return new RespostaPadronizada<>(HttpStatus.BAD_REQUEST, mensagem, mensagemTecnica, dados);
    }

    public static <T> RespostaPadronizada<T> badRequest(T dados) {
        return new RespostaPadronizada<>(HttpStatus.BAD_REQUEST, "Requisição inválida.", null, dados);
    }

    // ✅ 404 - Not Found
    public static <T> RespostaPadronizada<T> notFound(String mensagem) {
        return new RespostaPadronizada<>(HttpStatus.NOT_FOUND, mensagem, null,null);
    }

    public static <T> RespostaPadronizada<T> notFound() {
        return new RespostaPadronizada<>(HttpStatus.NOT_FOUND, "Recurso não encontrado.", null,null);
    }

    // 500 - Internal Server Error
    public static <T> RespostaPadronizada<T> internalServerError(String mensagem, String mensagemTecnica, T dados) {
        return new RespostaPadronizada<>(HttpStatus.INTERNAL_SERVER_ERROR, mensagem, mensagemTecnica, dados);
    }

    public static <T> RespostaPadronizada<T> internalServerError(T dados) {
        return new RespostaPadronizada<>(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", null, dados);
    }
}
