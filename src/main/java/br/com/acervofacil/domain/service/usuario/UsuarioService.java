package br.com.acervofacil.domain.service.usuario;

import br.com.acervofacil.api.dto.response.UsuarioResponseDTO;
import br.com.acervofacil.domain.entity.Usuario;

import java.util.UUID;

public interface UsuarioService {
    Usuario desativarUsuario(Usuario usuario);
    UsuarioResponseDTO buscarPorId(UUID id);
    UsuarioResponseDTO buscarPorFuncionarioId(UUID funcionarioId);
    UsuarioResponseDTO buscarPorClienteId(UUID clienteId);
}
