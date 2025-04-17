package br.com.acervofacil.api.dto.request;

import br.com.acervofacil.domain.enums.StatusEmprestimo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RequisicaoEmprestimoDTO(

        @NotNull(message = "O ID do livro não pode ser nulo.")
        UUID livroId,

        @NotNull(message = "O ID do cliente não pode ser nulo.")
        UUID clienteId,

        @NotNull(message = "O ID do funcionário responsável não pode ser nulo.")
        UUID funcionarioResponsavelId,

        @Min(value = 1, message = "A quantidade emprestada deve ser no mínimo 1.")
        @NotNull(message = "A quantidade emprestada não pode ser nula.")
        Integer quantidadeEmprestada,

        BigDecimal taxaEmprestimo,

        @NotNull(message = "A data de devolução prevista não pode ser nula.")
        @FutureOrPresent(message = "A data de devolução prevista deve ser no futuro ou na data atual.")
        LocalDateTime dataDevolucaoPrevista,

        StatusEmprestimo status
) {
    public RequisicaoEmprestimoDTO {
        if (status == null) {
            status = StatusEmprestimo.ATIVO; // Valor default para o status, caso não seja informado
        }
    }
}