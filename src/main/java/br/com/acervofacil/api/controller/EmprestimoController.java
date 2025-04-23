package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import br.com.acervofacil.domain.service.emprestimo.EmprestimoService;
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
@RequestMapping(value = "/api/v1/emprestimos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Empréstimos", description = "Operações relacionadas a empréstimos de livros, como cadastro, atualização, exclusão e consulta")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Operation(
            summary = "Cadastrar novo empréstimo",
            description = "Cria um novo empréstimo no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empréstimo cadastrado com sucesso", content = @Content(schema = @Schema(implementation = ResumoEmprestimoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<ResumoEmprestimoDTO>> cadastrar(
            @RequestBody @Valid RequisicaoEmprestimoDTO emprestimoDTO) {
        ResumoEmprestimoDTO emprestimoSalvo = emprestimoService.cadastrar(emprestimoDTO);
        URI uri = ApiUtils.getURI("/emprestimos/", emprestimoSalvo.getId());
        return ApiUtils.obterResponseEntityCreated(emprestimoSalvo, null, uri);
    }

    @Operation(
            summary = "Finalizar empréstimo",
            description = "Realiza a devolução de um livro emprestado e finaliza o empréstimo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimo finalizado com sucesso", content = @Content(schema = @Schema(implementation = ResumoEmprestimoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PutMapping(value = "/{uuid}/finalizar")
    public ResponseEntity<RespostaPadronizada<ResumoEmprestimoDTO>> finalizarEmprestimo(
            @PathVariable UUID uuid) {
        ResumoEmprestimoDTO emprestimoFinalizado = emprestimoService.finalizarEmprestimo(uuid);
        return ApiUtils.obterResponseEntityOk(emprestimoFinalizado, null);
    }

    @Operation(
            summary = "Atualizar status do empréstimo",
            description = "Atualiza o status de um empréstimo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do empréstimo atualizado com sucesso", content = @Content(schema = @Schema(implementation = ResumoEmprestimoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })

    @PutMapping(value = "/{uuid}/status")
    public ResponseEntity<RespostaPadronizada<ResumoEmprestimoDTO>> atualizarStatusEmprestimo(
            @PathVariable @NotNull(message = "O UUID do empréstimo é obrigatório") UUID uuid,
            @RequestParam @NotNull(message = "O status do empréstimo é obrigatório") StatusEmprestimo status,
            @RequestParam @NotNull(message = "O ID do funcionário responsável é obrigatório") UUID idFuncionarioResponsavel) {

        ResumoEmprestimoDTO emprestimoAtualizado = emprestimoService.atualizarStatusPorSupervisor(uuid, status, idFuncionarioResponsavel);
        return ApiUtils.obterResponseEntityOk(emprestimoAtualizado, null);
    }

    @Operation(
            summary = "Pagar empréstimo",
            description = "Finaliza o pagamento de um empréstimo com status 'AGUARDANDO_PAGAMENTO'. " +
                    "O pagamento inclui eventuais multas aplicadas e altera o status do empréstimo para 'CONCLUIDO'."
    )
    @PutMapping("/{uuid}/pagar")
    public ResponseEntity<RespostaPadronizada<ResumoEmprestimoDTO>> pagarEmprestimo(
            @PathVariable @NotNull(message = "O UUID do empréstimo é obrigatório") UUID uuid,
            @RequestParam @NotNull(message = "O ID do funcionário responsável é obrigatório") UUID idFuncionarioResponsavel) {
        emprestimoService.pagarEmprestimo(uuid, idFuncionarioResponsavel);
        return ApiUtils.obterResponseEntityOk(null, "Pagamento efetuado com sucesso.");
    }
}