package br.com.acervofacil.api.projections;

import br.com.acervofacil.domain.enums.StatusEmprestimo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ResumoEmprestimoProjecao {

    UUID getId();

    UUID getLivroId();

    String getLivroTitulo();

    UUID getClienteId();

    String getClienteNome();

    UUID getFuncionarioResponsavelId();

    String getFuncionarioResponsavelNome();

    Integer getQuantidadeEmprestada();

    BigDecimal getTaxaEmprestimo();

    LocalDateTime getDataEmprestimo();

    LocalDateTime getDataDevolucaoPrevista();

    LocalDateTime getDataDevolucaoReal();

    StatusEmprestimo getStatus();
}
