package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.utils.ApiUtils;
import br.com.acervofacil.domain.service.contato.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = "/api/v1/contatos"
)
@Tag(
        name = "Contatos",
        description = "Operações relacionadas a contatos vinculados a qualquer tipo de usuário do sistema (clientes, funcionários, etc.)"
)
public class ContatoController {

    private final ContatoService contatoService;

    @Operation(
            summary = "Buscar contato por ID",
            description = "Recupera os dados de um contato a partir de seu identificador único (UUID). O contato pode estar vinculado a qualquer tipo de usuário do sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contato encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = ContatoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Contato não encontrado",
            content = @Content
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<RespostaPadronizada<ContatoResponseDTO>> buscarPorId(
            @Parameter(
                    description = "ID do contato",
                    required = true,
                    example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            )
            @PathVariable @NotNull UUID id
    ) {
        ContatoResponseDTO contato = contatoService.obterContatoID(id);
        return ApiUtils.obterResponseEntityOk(contato, null);
    }
}
