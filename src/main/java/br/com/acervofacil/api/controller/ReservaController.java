package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.RequisicaoReservaDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.dto.response.ResumoReservaDTO;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.service.reserva.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/reservas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reservas", description = "Operações relacionadas à reserva de exemplares")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(
            summary = "Criar nova reserva",
            description = "Realiza a criação de uma nova reserva no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso", content = @Content(schema = @Schema(implementation = ResumoReservaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<ResumoReservaDTO>> criarReserva(
            @RequestBody @Valid RequisicaoReservaDTO reservaDTO) {

        ResumoReservaDTO reservaCriada = reservaService.criarReserva(reservaDTO);
        URI uri = ApiUtils.getURI("/reservas/", reservaCriada.getId());
        return ApiUtils.obterResponseEntityCreated(reservaCriada, "Reserva criada com sucesso", uri);
    }

    @Operation(
            summary = "Buscar reserva por ID",
            description = "Retorna os detalhes de uma reserva com base no UUID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada com sucesso", content = @Content(schema = @Schema(implementation = ResumoReservaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<RespostaPadronizada<ResumoReservaDTO>> buscarReservaPorId(
            @PathVariable @NotNull(message = "O UUID da reserva é obrigatório") UUID uuid) {

        ResumoReservaDTO resumoReservaDTO = reservaService.buscarReservaPorId(uuid);
        return ApiUtils.obterResponseEntityOk(resumoReservaDTO, "Reserva encontrada");
    }
}