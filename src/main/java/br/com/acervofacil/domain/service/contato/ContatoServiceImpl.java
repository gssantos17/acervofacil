package br.com.acervofacil.domain.service.contato;

import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.api.dto.mapper.ContatoMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Contato;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.domain.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContatoServiceImpl implements ContatoService {

    private final ContatoRepository contatoRepository;
    private final ContatoMapper contatoMapper;

    @Cacheable(value = "contatoPorId", key = "#id")
    @Override
    public ContatoResponseDTO obterContatoID(UUID id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Contato com ID " + id + " não encontrado."));
        return contatoMapper.contatoToContatoDTO(contato);
    }

    @Override
    public ContatoResponseDTO obterContatoCliente(Cliente cliente) {
        Contato contato = contatoRepository.findById(cliente.getContato().getId())
                .orElseThrow(() -> new NaoEncontradoException("Contato do cliente não encontrado."));
        return contatoMapper.contatoToContatoDTO(contato);
    }

    @Override
    public ContatoResponseDTO obterContatoFuncionario(Funcionario funcionario) {
        Contato contato = contatoRepository.findById(funcionario.getContato().getId())
                .orElseThrow(() -> new NaoEncontradoException("Contato do cliente não encontrado."));
        return contatoMapper.contatoToContatoDTO(contato);
    }
}
