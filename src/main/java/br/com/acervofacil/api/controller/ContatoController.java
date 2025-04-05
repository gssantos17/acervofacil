package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.service.contato.ContatoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPadronizada<ContatoResponseDTO>> buscarPorId(@PathVariable @NotNull UUID id) {
        ContatoResponseDTO contato = contatoService.obterContatoID(id);
        return ApiUtils.obterResponseEntityOk(contato, null);
    }
}