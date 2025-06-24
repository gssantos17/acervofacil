package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.domain.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumoReservaDTO {

    private UUID id;

    private String tituloLivro;
    private UUID livroId;

    private String nomeCliente;
    private UUID clienteId;

    private String nomeFuncionario;
    private UUID funcionarioId;

    private LocalDateTime dataReserva;
    private LocalDateTime dataExpiracao;

    private StatusReserva status;
}