package br.com.acervofacil.domain.exception;

import br.com.acervofacil.api.dto.response.ErroValidacaoDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespostaPadronizada.internalServerError("Erro interno no servidor.", ex.getMessage(), null));
    }
}
