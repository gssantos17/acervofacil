    package br.com.acervofacil.api.dto.response;

    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.Getter;
    import org.springframework.http.HttpStatus;
    import java.time.LocalDateTime;

    @Getter
    @Schema(name = "RespostaPadronizada", description = "Estrutura padrão de resposta da API")
    public class RespostaPadronizada<T> {

        @Schema(description = "Data e hora em que a resposta foi gerada", example = "2025-04-04T15:22:35.123")
        private final LocalDateTime timestamp;

        @Schema(description = "Código HTTP da resposta", example = "200")
        private final int status;

        @Schema(description = "Mensagem amigável da resposta", example = "Operação realizada com sucesso.")
        private final String mensagem;

        @Schema(description = "Mensagem técnica para debug (pode ser nula)", example = "Detalhes técnicos do erro, se houver")
        private final String mensagemTecnica;

        @Schema(description = "Objeto com os dados retornados pela API")
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
