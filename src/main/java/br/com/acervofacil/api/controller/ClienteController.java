package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.response.*;
import br.com.acervofacil.api.projections.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.utils.ApiUtils;
import br.com.acervofacil.domain.service.cliente.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Clientes", description = "Operações relacionadas aos clientes, como cadastro, atualização, exclusão e consulta")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(
            summary = "Cadastrar novo cliente",
            description = "Cria um novo cliente no sistema com CPF único e campos obrigatórios validados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> cadastrar(
            @RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteSalvo = clienteService.salvar(clienteDTO);
        URI uri = ApiUtils.getURI("/clientes/", clienteSalvo.getId());
        return ApiUtils.obterResponseEntityCreated(clienteSalvo, null, uri);
    }

    @Operation(summary = "Deletar cliente por ID", description = "Desativa logicamente o cliente com o ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<RespostaPadronizada<Object>> deletar(
            @Parameter(description = "ID do cliente", required = true) @PathVariable @NotNull UUID uuid) {
        clienteService.deletar(uuid);
        return ApiUtils.obterResponseEntityNoContent("Cliente Desativado Com sucesso");
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> atualizar(
            @Parameter(description = "ID do cliente", required = true) @PathVariable @NotNull UUID id,
            @RequestBody @Valid ClienteUpdateDTO clienteUpdateDTO) {
        ClienteResponseDTO clienteSalvo = clienteService.atualizar(id, clienteUpdateDTO);
        return ApiUtils.obterResponseEntityOk(clienteSalvo, null);
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> buscarPorId(
            @Parameter(description = "ID do cliente", required = true) @PathVariable @NotNull UUID uuid) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorId(uuid);
        return ApiUtils.obterResponseEntityOk(clienteResponseDTO, null);
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> buscarPorCpf(
            @Parameter(description = "CPF do cliente", example = "12345678900", required = true) @RequestParam @NotBlank String cpf) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorCpf(cpf);
        return ApiUtils.obterResponseEntityOk(clienteResponseDTO, null);
    }

    @Operation(summary = "Buscar cliente por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClienteComEnderecoContatoProjecao.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente(s) não encontrado(s)",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetro 'nome' deve conter pelo menos 3 caracteres",
                    content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))
            )
    })
    @GetMapping("/buscarPorNome")
    public ResponseEntity<RespostaPadronizada<List<ClienteComEnderecoContatoProjecao>>> buscarPorNome(
            @Parameter(description = "Nome do cliente (mínimo 3 caracteres)", example = "Gabriel Silva", required = true)
            @RequestParam
            @NotBlank(message = "O parâmetro 'nome' não pode estar vazio")
            @Length(min = 3, message = "O parâmetro 'nome' deve conter pelo menos 3 caracteres") String nome) {
        return ApiUtils.obterResponseEntityOk(clienteService.buscarPorNome(nome), null);
    }

    @Operation(summary = "Listar clientes paginados", description = "Retorna uma lista paginada de clientes com seus contatos e endereços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(schema = @Schema(implementation = PaginacaoCustomizada.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado")
    })
    @GetMapping
    public ResponseEntity<RespostaPadronizada<PaginacaoCustomizada<ClienteComEnderecoContatoProjecao>>> listarClientes(
            @Parameter(description = "Número da página", example = "0", in = ParameterIn.QUERY) @PositiveOrZero @RequestParam int page,
            @Parameter(description = "Tamanho da página", example = "10", in = ParameterIn.QUERY) @PositiveOrZero @RequestParam int size) {
        PaginacaoCustomizada<ClienteComEnderecoContatoProjecao> paginacao = clienteService.obterClientesPaginados(page, size);

        if (paginacao.getTotalElementos() > 0) {
            return ApiUtils.obterResponseEntityOk(paginacao, null);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}