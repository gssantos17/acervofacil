package br.com.acervofacil.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErroValidacaoDTO {
    private String campo;
    private Object valorRejeitado;
    private String mensagem;
}
