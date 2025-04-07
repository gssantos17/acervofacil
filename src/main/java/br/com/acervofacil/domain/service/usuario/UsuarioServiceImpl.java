package br.com.acervofacil.domain.service.usuario;

import br.com.acervofacil.api.dto.response.UsuarioResponseDTO;
import br.com.acervofacil.configuration.mapper.UsuarioMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Usuario;
import br.com.acervofacil.domain.enums.StatusUsuario;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.repository.cliente.ClienteRepository;
import br.com.acervofacil.domain.repository.Usuariorepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final Usuariorepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ClienteRepository clienteRepository;

    public Usuario desativarUsuario(Usuario usuario) {
        this.validarStatusUsuario(usuario, true);
        usuario.setStatus(StatusUsuario.INATIVO);
        return usuarioRepository.save(usuario);
    }

    public Usuario ativarUsuario(Usuario usuario) {
        this.validarStatusUsuario(usuario, false);
        usuario.setStatus(StatusUsuario.ATIVO);
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário com ID " + id + " não encontrado."));
        return usuarioMapper.usuarioToUsuarioDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO buscarPorFuncionarioId(UUID funcionarioId) {
        // implementar lógica similar para funcionario quando necessário
        return null;
    }

    @Override
    public UsuarioResponseDTO buscarPorClienteId(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NaoEncontradoException("Cliente com ID " + clienteId + " não encontrado."));

        Usuario usuario = cliente.getUsuario();
        if (usuario == null) {
            throw new NaoEncontradoException("Cliente não possui usuário vinculado.");
        }

        if( StatusUsuario.INATIVO.equals( usuario.getStatus() )){
            throw new ServiceException("Cliente não possui usuário ativo.");
        }

        return usuarioMapper.usuarioToUsuarioDTO(usuario);
    }

    private void validarStatusUsuario(Usuario usuario, boolean isInativar) {
        if (StatusUsuario.INATIVO.equals(usuario.getStatus()) && isInativar) {
            throw new ServiceException("O Usuário já está desabilitado.");
        }
        if (StatusUsuario.ATIVO.equals(usuario.getStatus()) && !isInativar) {
            throw new ServiceException("O Usuário já está ativo.");
        }
    }
}
