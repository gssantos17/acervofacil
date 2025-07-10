package br.com.acervofacil.domain.service.cliente;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.projections.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.api.dto.mapper.ClienteMapper;
import br.com.acervofacil.api.dto.mapper.ContatoMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Contato;
import br.com.acervofacil.domain.entity.Endereco;
import br.com.acervofacil.domain.entity.Usuario;
import br.com.acervofacil.domain.exception.ClienteNotFoundException;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.repository.cliente.ClienteRepository;
import br.com.acervofacil.domain.repository.ContatoRepository;
import br.com.acervofacil.domain.repository.EnderecoRepository;
import br.com.acervofacil.domain.service.usuario.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ContatoMapper contatoMapper;
    private final UsuarioServiceImpl usuarioService;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    @Override
    public ClienteResponseDTO salvar(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByCpf(clienteDTO.cpf())) {
            throw new ServiceException("Cliente já existe - " + clienteDTO.cpf());
        }

        Cliente clienteSalvo = clienteRepository.save(clienteMapper.clienteDTOToCliente(clienteDTO));
        return clienteMapper.clienteToClienteResponseDTO(clienteSalvo);
    }

    @CacheEvict(value = {"clientesById", "clientesByCpf"}, key = "#id")
    @Transactional
    @Override
    public ClienteResponseDTO atualizar(UUID id, ClienteUpdateDTO clienteDTO) {
        Cliente cliente = this.buscarEntidade(id);

        Cliente clienteAtualizado = clienteMapper.clienteUpdateDTOToCliente(clienteDTO);
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setDataNascimento(clienteAtualizado.getDataNascimento());

        if (clienteDTO.contato() != null) {
            Contato contato = clienteAtualizado.getContato();
            contato.setId(cliente.getContato().getId());
            contato = contatoRepository.save(contato);
            cliente.setContato(contato);
        }

        if (clienteDTO.endereco() != null) {
            Endereco endereco = clienteAtualizado.getEndereco();
            endereco.setId(cliente.getEndereco().getId());
            endereco = enderecoRepository.save(endereco);
            cliente.setEndereco(endereco);
        }

        cliente = this.clienteRepository.save(cliente);
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    @Transactional
    @CacheEvict(value = {"clientesById", "clientesByCpf"}, key = "#id")
    @Override
    public void deletar(UUID id) {
        Cliente cliente = obterClientePorId(id);
        Usuario usuarioAtualizado = usuarioService.desativarUsuario(cliente.getUsuario());
        cliente.setUsuario(usuarioAtualizado);
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarEntidade(Object identificador) {
        if (identificador == null) {
            throw new ServiceException("Identificador Inválido");
        }

        Cliente cliente;

        if (identificador instanceof UUID id) {
            cliente = this.obterClientePorId(id);
            return cliente;
        }

        String cpf = identificador.toString();
        cliente = this.obterClientePorCPF(cpf);
        return cliente;
    }

    @Cacheable(value = "clientesById", key = "#id")
    @Override
    public ClienteResponseDTO buscarPorId(UUID id) {
        Cliente cliente = this.obterClientePorId(id);
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    @Cacheable(value = "clientesByCpf", key = "#cpf")
    @Override
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        Cliente cliente = this.obterClientePorCPF(cpf);
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    @Override
    public PaginacaoCustomizada<ClienteComEnderecoContatoProjecao> obterClientesPaginados(int page, int size) {
        Page<ClienteComEnderecoContatoProjecao> clientesPage = clienteRepository.findAllBy(PageRequest.of(page, size));
        return new PaginacaoCustomizada<>(clientesPage);
    }

    private Cliente obterClientePorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado - ID: " + id));
    }

    private Cliente obterClientePorCPF(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ServiceException("Cliente não encontrado com o CPF fornecido: " + cpf));
    }
}