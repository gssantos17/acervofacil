package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.dto.response.UsuarioResponseDTO;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.service.usuario.UsuarioService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPadronizada<UsuarioResponseDTO>> buscarPorId(
            @PathVariable @NotNull UUID id) {
        UsuarioResponseDTO usuarioResponse = usuarioService.buscarPorId(id);
        return ApiUtils.obterResponseEntityOk(usuarioResponse, null);
    }

    @GetMapping("/por-cliente/{clienteId}")
    public ResponseEntity<RespostaPadronizada<UsuarioResponseDTO>> buscarPorClienteId(
            @PathVariable @NotNull UUID clienteId) {
        UsuarioResponseDTO usuarioResponse = usuarioService.buscarPorClienteId(clienteId);
        return ApiUtils.obterResponseEntityOk(usuarioResponse, null);
    }

    @GetMapping("/por-funcionario/{funcionarioId}")
    public ResponseEntity<RespostaPadronizada<UsuarioResponseDTO>> buscarPorFuncionarioId(
            @PathVariable @NotNull UUID funcionarioId) {
        UsuarioResponseDTO usuarioResponse = usuarioService.buscarPorFuncionarioId(funcionarioId);
        return ApiUtils.obterResponseEntityOk(usuarioResponse, null);
    }
}
