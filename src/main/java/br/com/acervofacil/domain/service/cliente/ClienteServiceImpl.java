package br.com.acervofacil.domain.service.cliente;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.request.UsuarioDTO;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.configuration.mapper.ClienteMapper;
import br.com.acervofacil.configuration.mapper.ContatoMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Contato;
import br.com.acervofacil.domain.entity.Endereco;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.repository.ClienteRepository;
import br.com.acervofacil.domain.repository.ContatoRepository;
import br.com.acervofacil.domain.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ContatoMapper contatoMapper;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    @Override
    public ClienteResponseDTO salvar(ClienteDTO clienteDTO) {
        // Verificar se o CPF já está cadastrado
        if (clienteRepository.existsByCpf(clienteDTO.cpf())) {
            throw new ServiceException("Cliente já existe - " + clienteDTO.cpf());
        }

        // Converter DTO para Entidade usando o Mapper e salvar
        Cliente clienteSalvo = clienteRepository.save(clienteMapper.clienteDTOToCliente(clienteDTO));

        // Retornar DTO correspondente usando o Mapper
        return clienteMapper.clienteToClienteResponseDTO(clienteSalvo);
    }

    @Override
    public ClienteResponseDTO atualizar(UUID id, ClienteUpdateDTO clienteDTO) {
        Cliente cliente = this.buscarEntidade(id);

        Cliente clienteAtualizado = clienteMapper.clienteUpdateDTOToCliente(clienteDTO);
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setDataNascimento(clienteAtualizado.getDataNascimento());

        if( clienteDTO.contato() != null ) {
            Contato contato = clienteAtualizado.getContato();
            contato.setId(cliente.getContato().getId());
            contato = contatoRepository.save(contato);
            cliente.setContato(contato);
        }
        if( clienteDTO.endereco() != null ) {
            Endereco endereco = clienteAtualizado.getEndereco();
            endereco.setId(cliente.getEndereco().getId());
            endereco = enderecoRepository.save(endereco);
            cliente.setEndereco( endereco );
        }

        cliente = this.clienteRepository.save(cliente);
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }


    @Override
    public void deletar(UUID id) {

    }

    @Override
    public Cliente buscarEntidade(Object identificador) {
        if (identificador == null) {
            throw new ServiceException("Identificador Inválido");
        }

        Cliente cliente;

        if (identificador instanceof UUID) {
            UUID id = (UUID) identificador;
            cliente = this.clienteRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cliente não encontrado com o ID fornecido: " + id));
            return cliente;
        }

        String cpf = identificador.toString();
        cliente = this.clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ServiceException("Cliente não encontrado com o CPF fornecido: " + cpf));

        return cliente;
    }

    @Override
    public Optional<ClienteResponseDTO> buscarPorId(UUID id) {
        return null;
    }

    @Override
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        // Tenta buscar o cliente no repositório pelo CPF fornecido
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ServiceException("Cliente não encontrado - CPF: " + cpf));

        // Converte a entidade Cliente para o DTO ClienteResponseDTO
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    @Override
    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        return null;
    }
}
