package br.com.acervofacil.domain.exception;

import br.com.acervofacil.api.dto.response.ErroValidacaoDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaPadronizada<List<ErroValidacaoDTO>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErroValidacaoDTO> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(erro -> new ErroValidacaoDTO(
                        erro.getField(),
                        erro.getRejectedValue(),
                        erro.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                RespostaPadronizada.badRequest(
                        "Requisição inválida.",
                        null,
                        erros
                )
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<RespostaPadronizada<List<ErroValidacaoDTO>>> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        List<ErroValidacaoDTO> erros = ex.getAllErrors()
                .stream()
                .map(error -> {
                    String campo = error instanceof ObjectError ? ((ObjectError) error).getObjectName() : "parâmetro";
                    return new ErroValidacaoDTO(
                            campo,
                            null,
                            error.getDefaultMessage()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                RespostaPadronizada.badRequest(
                        "Requisição inválida.",
                        null,
                        erros
                )
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespostaPadronizada<List<ErroValidacaoDTO>>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String nomeParametro = ex.getName(); // Ex: "id"
        Object valorInformado = ex.getValue(); // Ex: "abc"
        String mensagem = String.format(
                "O valor '%s' informado para o parâmetro '%s' é inválido. Esperado: %s.",
                valorInformado,
                nomeParametro,
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "tipo desconhecido"
        );

        ErroValidacaoDTO erro = new ErroValidacaoDTO(
                nomeParametro,
                valorInformado,
                mensagem
        );

        return ResponseEntity.badRequest().body(
                RespostaPadronizada.badRequest(
                        "Parâmetro inválido na requisição.",
                        null,
                        List.of(erro)
                )
        );
    }


    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<RespostaPadronizada<String>> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespostaPadronizada.notFound(ex.getMessage()));
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<RespostaPadronizada<String>> handleNaoEncontradoException(NaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespostaPadronizada.notFound(ex.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RespostaPadronizada<String>> handleServiceException(ServiceException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RespostaPadronizada.badRequest("Requisição Inválida.",ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaPadronizada<String>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespostaPadronizada.internalServerError("Erro interno no servidor.", ex.getMessage(), null));
    }
}
