package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.domain.enums.StatusEmprestimo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ResumoEmprestimoDTO {

    private UUID id;

    private UUID livroId;

    private String livroTitulo;

    private UUID clienteId;

    private String clienteNome;

    private UUID funcionarioResponsavelId;

    private String funcionarioResponsavelNome;

    private Integer quantidadeEmprestada;

    private BigDecimal taxaEmprestimo;

    private ResumoMultaDTO multa;

    private LocalDateTime dataEmprestimo;

    private LocalDateTime dataDevolucaoPrevista;

    private LocalDateTime dataDevolucaoReal;

    private StatusEmprestimo status;
}