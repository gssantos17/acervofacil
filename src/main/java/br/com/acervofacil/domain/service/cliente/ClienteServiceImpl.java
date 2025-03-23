package br.com.acervofacil.domain.service.cliente;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.configuration.mapper.ClienteMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.repository.ClienteRepository;
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
    public ClienteResponseDTO atualizar(UUID id, ClienteDTO clienteDTO) {
        return null;
    }

    @Override
    public void deletar(UUID id) {

    }

    @Override
    public Optional<Cliente> buscarEntidade(Object identificador) {
        if (identificador == null)
            throw new ServiceException("Identificador Inválido - " + identificador);

        Cliente cliente = null;
        if (identificador instanceof UUID)
            cliente = this.clienteRepository.findById((UUID) identificador)
                    .orElseThrow(() -> new ServiceException("Cliente não existe - ID " + (UUID) identificador));
        else
            cliente = this.clienteRepository.findByCpf(identificador.toString())
                    .orElseThrow(() -> new ServiceException("Cliente não existe - CPF " + identificador.toString()));

        return Optional.of(cliente);
    }

    @Override
    public Optional<ClienteResponseDTO> buscarPorId(UUID id) {
        return Optional.empty();
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
