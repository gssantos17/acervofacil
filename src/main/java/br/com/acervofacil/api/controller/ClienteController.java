package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.response.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.service.cliente.ClienteService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> cadastrar(@RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteSalvo = clienteService.salvar(clienteDTO);
        URI uri = ApiUtils.getURI("/clientes/", clienteSalvo.getId());
        return ApiUtils.obterResponseEntityCreated(clienteSalvo,null, uri);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<RespostaPadronizada<Object>> deletar(
            @PathVariable @NotNull(message = "O ID não pode ser nulo.") UUID uuid) {
        clienteService.deletar(uuid);
        return ApiUtils.obterResponseEntityNoContent("Cliente Desativado Com sucesso");
    }

    @PutMapping("{id}")
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> atualizar(@PathVariable @NotNull UUID id, @RequestBody @Valid ClienteUpdateDTO clienteUpdateDTO) {
        ClienteResponseDTO clienteSalvo = clienteService.atualizar(id, clienteUpdateDTO);
        return ApiUtils.obterResponseEntityOk(clienteSalvo, null);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> buscarPorId(@NotNull @PathVariable UUID uuid) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorId(uuid);
        return ApiUtils.obterResponseEntityOk(clienteResponseDTO, null);
    }

    @GetMapping("buscar")
    public ResponseEntity<RespostaPadronizada<ClienteResponseDTO>> buscarPorCpf(@RequestParam @NotBlank String cpf) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorCpf(cpf);
        return ApiUtils.obterResponseEntityOk(clienteResponseDTO, null);
    }

    @GetMapping
    public ResponseEntity<RespostaPadronizada<PaginacaoCustomizada<ClienteComEnderecoContatoProjecao>>>  getClientes(
            @PositiveOrZero @RequestParam int page, @PositiveOrZero @RequestParam int size) {
        PaginacaoCustomizada<ClienteComEnderecoContatoProjecao> paginacao = clienteService.obterClientesPaginados(page, size);
        if (paginacao.getTotalElementos() > 0)
            return ApiUtils.obterResponseEntityOk(paginacao, null);
        else
            return ResponseEntity.noContent().build();
    }
}
