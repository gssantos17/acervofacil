package br.com.acervofacil.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequisicaoReservaDTO(

        @NotNull(message = "O ID do cliente é obrigatório")
        UUID clienteId,

        @NotNull(message = "O ID do livro é obrigatório")
        UUID livroId,

        @NotNull(message = "O ID do funcionário é obrigatório")
        UUID funcionarioId

) {}