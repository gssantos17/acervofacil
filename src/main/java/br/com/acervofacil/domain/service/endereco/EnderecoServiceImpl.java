package br.com.acervofacil.domain.service.endereco;

import br.com.acervofacil.api.dto.response.EnderecoResponseDTO;
import br.com.acervofacil.api.dto.mapper.EnderecoMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.domain.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Override
    public EnderecoResponseDTO obterEnderecoID(UUID id) {
        return enderecoRepository.findById(id)
                .map(enderecoMapper::enderecoToEnderecoDTO)
                .orElseThrow(() -> new NaoEncontradoException("Endereço com ID " + id + " não encontrado."));
    }

    @Override
    public EnderecoResponseDTO obterEnderecoCliente(Cliente cliente) {
        if (cliente.getEndereco() == null) {
            throw new NaoEncontradoException("Cliente não possui endereço cadastrado.");
        }
        return enderecoMapper.enderecoToEnderecoDTO(cliente.getEndereco());
    }

    @Override
    public EnderecoResponseDTO obterEnderecoFuncionario(Funcionario funcionario) {
        if (funcionario.getEndereco() == null) {
            throw new NaoEncontradoException("Funcionário não possui endereço cadastrado.");
        }
        return enderecoMapper.enderecoToEnderecoDTO(funcionario.getEndereco());
    }
}
