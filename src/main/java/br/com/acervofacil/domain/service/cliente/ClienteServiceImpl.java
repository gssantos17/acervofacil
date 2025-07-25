package br.com.acervofacil.domain.service.cliente;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.projections.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.api.dto.mapper.ClienteMapper;
import br.com.acervofacil.api.dto.mapper.ContatoMapper;
import br.com.acervofacil.api.projections.ClienteResumoProjecao;
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
import br.com.acervofacil.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        atualizarContatoSeNecessario(cliente, clienteAtualizado, clienteDTO);
        atualizarEnderecoSeNecessario(cliente, clienteAtualizado, clienteDTO);

        cliente = this.clienteRepository.save(cliente);
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    @Transactional
    @CacheEvict(value = {"clientesById", "clientesByCpf", "resumoClientesByCpf", "resumoClientesById"}, key = "#id")
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
    public List<ClienteComEnderecoContatoProjecao> buscarPorNome(String nome) {
        var dados = clienteRepository.findAllByNome(nome);
        if( dados == null || dados.isEmpty()) {
            throw new ClienteNotFoundException("Não existe cliente para o critério de busca: " + nome);
        }
        return dados;
    }

    @Override
    public PaginacaoCustomizada<ClienteComEnderecoContatoProjecao> obterClientesPaginados(int page, int size) {
        Page<ClienteComEnderecoContatoProjecao> clientesPage = clienteRepository.findAllBy(PageRequest.of(page, size));
        return new PaginacaoCustomizada<>(clientesPage);
    }

    @Cacheable(value = "resumoClientesById", key = "#id")
    @Override
    public ClienteResumoProjecao buscarResumoClientePorId(UUID id) {
        return ServiceUtils.obterOuLancar(
                clienteRepository.findResumoById(id),
                "Cliente",
                id.toString()
        );
    }

    @Cacheable(value = "resumoClientesByCpf", key = "#cpf")
    @Override
    public ClienteResumoProjecao buscarResumoClientePorCpf(String cpf) {
        return ServiceUtils.obterOuLancar(
                clienteRepository.findResumoByCpf(cpf),
                "Cliente",
                cpf
        );
    }


    private Cliente obterClientePorId(UUID id) {
        return ServiceUtils.obterOuLancar(
                clienteRepository.findById(id),
                "Cliente",
                id.toString()
        );
    }

    private Cliente obterClientePorCPF(String cpf) {
        return ServiceUtils.obterOuLancar(
                clienteRepository.findByCpf(cpf),
                "Cliente",
                cpf
        );
    }

    private void atualizarContatoSeNecessario(Cliente cliente, Cliente clienteAtualizado, ClienteUpdateDTO dto) {
        if (dto.contato() != null && cliente.getContato() != null) {
            Contato novoContato = clienteAtualizado.getContato();
            novoContato.setId(cliente.getContato().getId());
            Contato contatoSalvo = contatoRepository.save(novoContato);
            cliente.setContato(contatoSalvo);
        }
    }

    private void atualizarEnderecoSeNecessario(Cliente cliente, Cliente clienteAtualizado, ClienteUpdateDTO dto) {
        if (dto.endereco() != null && cliente.getEndereco() != null) {
            Endereco novoEndereco = clienteAtualizado.getEndereco();
            novoEndereco.setId(cliente.getEndereco().getId());
            Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);
            cliente.setEndereco(enderecoSalvo);
        }
    }
}