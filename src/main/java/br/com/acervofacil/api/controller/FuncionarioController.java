package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.FuncionarioDTO;
import br.com.acervofacil.api.dto.request.FuncionarioUpdateDTO;
import br.com.acervofacil.api.dto.response.*;
import br.com.acervofacil.api.projections.FuncionarioComPathsProjecao;
import br.com.acervofacil.utils.ApiUtils;
import br.com.acervofacil.domain.service.funcionario.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/funcionarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários, como cadastro, atualização, exclusão e consulta")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Operation(summary = "Cadastrar novo funcionário",
            description = "Cria um novo funcionário no sistema com CPF único e campos obrigatórios validados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<FuncionarioResponseDTO>> cadastrar(
            @RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        FuncionarioResponseDTO funcionarioSalvo = funcionarioService.salvar(funcionarioDTO);
        URI uri = ApiUtils.getURI("/funcionarios/", funcionarioSalvo.getId());
        return ApiUtils.obterResponseEntityCreated(funcionarioSalvo, null, uri);
    }

    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RespostaPadronizada<FuncionarioResponseDTO>> buscarPorId(
            @Parameter(description = "ID do funcionário", required = true) @PathVariable @NotNull UUID id) {
        FuncionarioResponseDTO response = funcionarioService.buscarPorId(id);
        return ApiUtils.obterResponseEntityOk(response, null);
    }

    @Operation(summary = "Buscar funcionário por CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<RespostaPadronizada<FuncionarioResponseDTO>> buscarPorCpf(
            @Parameter(description = "CPF do funcionário", example = "123.456.789-00", required = true)
            @RequestParam @NotBlank String cpf) {
        FuncionarioResponseDTO response = funcionarioService.buscarPorCpf(cpf);
        return ApiUtils.obterResponseEntityOk(response, null);
    }

    @Operation(summary = "Atualizar funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<FuncionarioResponseDTO>> atualizar(
            @Parameter(description = "ID do funcionário", required = true) @PathVariable @NotNull UUID id,
            @RequestBody @Valid FuncionarioUpdateDTO funcionarioDTO) {
        FuncionarioResponseDTO atualizado = funcionarioService.atualizar(id, funcionarioDTO);
        return ApiUtils.obterResponseEntityOk(atualizado, null);
    }

    @Operation(summary = "Deletar funcionário por ID",
            description = "Desativa logicamente o funcionário com o ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaPadronizada<Object>> deletar(
            @Parameter(description = "ID do funcionário", required = true) @PathVariable @NotNull UUID id) {
        funcionarioService.deletar(id);
        return ApiUtils.obterResponseEntityNoContent("Funcionário desativado com sucesso");
    }

    @Operation(summary = "Listar funcionários paginados",
            description = "Retorna uma lista paginada de funcionários com seus contatos e endereços.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados",
                    content = @Content(schema = @Schema(implementation = PaginacaoCustomizada.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado")
    })
    @GetMapping
    public ResponseEntity<RespostaPadronizada<PaginacaoCustomizada<FuncionarioComPathsProjecao>>> listarPaginado(
            @Parameter(description = "Número da página", example = "0", in = ParameterIn.QUERY)
            @PositiveOrZero @RequestParam int page,
            @Parameter(description = "Tamanho da página", example = "10", in = ParameterIn.QUERY)
            @PositiveOrZero @RequestParam int size) {

        var paginacao = funcionarioService.obterFuncionariosPaginados(page, size);

        if (paginacao.getTotalElementos() > 0 ) {
            return ApiUtils.obterResponseEntityOk(paginacao, null);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}