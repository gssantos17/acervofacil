package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.response.EnderecoResponseDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.utils.ApiUtils;
import br.com.acervofacil.domain.service.endereco.EnderecoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPadronizada<EnderecoResponseDTO>> buscarPorId(@PathVariable @NotNull UUID id) {
        EnderecoResponseDTO endereco = enderecoService.obterEnderecoID(id);
        return ApiUtils.obterResponseEntityOk(endereco, null);
    }
}
