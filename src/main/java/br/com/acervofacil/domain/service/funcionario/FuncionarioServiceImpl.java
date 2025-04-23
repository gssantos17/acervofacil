package br.com.acervofacil.domain.service.funcionario;

import br.com.acervofacil.api.dto.request.FuncionarioDTO;
import br.com.acervofacil.api.dto.request.FuncionarioUpdateDTO;
import br.com.acervofacil.api.projections.FuncionarioComPathsProjecao;
import br.com.acervofacil.api.dto.response.FuncionarioResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.configuration.mapper.FuncionarioMapper;
import br.com.acervofacil.domain.entity.Contato;
import br.com.acervofacil.domain.entity.Endereco;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Usuario;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.domain.repository.ContatoRepository;
import br.com.acervofacil.domain.repository.EnderecoRepository;
import br.com.acervofacil.domain.repository.funcionario.FuncionarioRepository;
import br.com.acervofacil.domain.service.usuario.UsuarioServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioServiceImpl usuarioService;

    @Transactional
    @Override
    public FuncionarioResponseDTO salvar(FuncionarioDTO funcionarioDTO) {
        if (funcionarioRepository.existsByCpf(funcionarioDTO.cpf())) {
            throw new IllegalArgumentException("Funcionário já cadastrado - CPF: " + funcionarioDTO.cpf());
        }

        Funcionario funcionario = funcionarioMapper.funcionarioDTOToFuncionario(funcionarioDTO);
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return funcionarioMapper.funcionarioToFuncionarioResponseDTO(salvo);
    }

    @Transactional
    @CacheEvict(value = {"funcionariosById", "funcionariosByCpf"}, key = "#id")
    @Override
    public FuncionarioResponseDTO atualizar(UUID id, FuncionarioUpdateDTO funcionarioDTO) {
        Funcionario funcionario = buscarFuncionarioPorIdOuLancar(id);
        Funcionario atualizado = funcionarioMapper.atualizarFuncionarioAPartirDTO(funcionarioDTO);

        funcionario.setNome(atualizado.getNome());
        funcionario.setCpf(atualizado.getCpf());
        funcionario.setDataNascimento(atualizado.getDataNascimento());
        funcionario.setCargo(atualizado.getCargo());

        if (funcionarioDTO.contato() != null) {
            Contato contato = atualizado.getContato();
            contato.setId(funcionario.getContato().getId());
            contato = contatoRepository.save(contato);
            funcionario.setContato(contato);
        }

        if (funcionarioDTO.endereco() != null) {
            Endereco endereco = atualizado.getEndereco();
            endereco.setId(funcionario.getEndereco().getId());
            endereco = enderecoRepository.save(endereco);
            funcionario.setEndereco(endereco);
        }

        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.funcionarioToFuncionarioResponseDTO(funcionario);
    }

    @Transactional
    @CacheEvict(value = {"funcionariosById", "funcionariosByCpf"}, key = "#id")
    @Override
    public void deletar(UUID id) {
        Funcionario funcionario = buscarFuncionarioPorIdOuLancar(id);
        Usuario usuarioDesativado = usuarioService.desativarUsuario(funcionario.getUsuario());
        funcionario.setUsuario(usuarioDesativado);
        funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario buscarEntidade(Object identificador) {
        if (identificador == null) throw new IllegalArgumentException("Identificador inválido");

        if (identificador instanceof UUID uuid) {
            return buscarFuncionarioPorIdOuLancar(uuid);
        }

        return buscarFuncionarioPorCpfOuLancar(identificador.toString());
    }

    @Override
    @Cacheable(value = "funcionariosById", key = "#id")
    public FuncionarioResponseDTO buscarPorId(UUID id) {
        return funcionarioMapper.funcionarioToFuncionarioResponseDTO(buscarFuncionarioPorIdOuLancar(id));
    }

    @Override
    @Cacheable(value = "funcionariosByCpf", key = "#cpf")
    public FuncionarioResponseDTO buscarPorCpf(String cpf) {
        return funcionarioMapper.funcionarioToFuncionarioResponseDTO(buscarFuncionarioPorCpfOuLancar(cpf));
    }

    @Override
    public PaginacaoCustomizada<FuncionarioComPathsProjecao> obterFuncionariosPaginados(int page, int size) {
        var pageResult = funcionarioRepository.findAllComEnderecoContatoUsuario(PageRequest.of(page, size));
        return new PaginacaoCustomizada<>(pageResult);
    }

    private Funcionario buscarFuncionarioPorIdOuLancar(UUID id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Funcionário com ID " + id + " não encontrado"));
    }

    private Funcionario buscarFuncionarioPorCpfOuLancar(String cpf) {
        return funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new NaoEncontradoException("Funcionário com CPF " + cpf + " não encontrado"));
    }
}