package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.domain.service.cliente.ClienteService;
import br.com.acervofacil.domain.exception.ServiceException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteDTO clienteDTO) {
        try {
            ClienteResponseDTO clienteSalvo = clienteService.salvar(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> cadastrar(@PathVariable @NotNull UUID id, @RequestBody @Valid ClienteUpdateDTO clienteUpdateDTO) {
        try {
            ClienteResponseDTO clienteSalvo = clienteService.atualizar(id,clienteUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(clienteSalvo);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscar(@PathVariable String cpf) {
        try {
            // Chama o serviço para buscar o cliente pelo CPF
            ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorCpf(cpf);

            // Retorna o cliente com status 200 OK se encontrado
            return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
        } catch (ServiceException e) {
            // Retorna erro 404 se o cliente não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
