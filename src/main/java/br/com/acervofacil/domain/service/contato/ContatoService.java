package br.com.acervofacil.domain.service.contato;

import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;

import java.util.UUID;

public interface ContatoService {
    ContatoResponseDTO obterContatoID(UUID id);
    ContatoResponseDTO obterContatoCliente(Cliente cliente);
    ContatoResponseDTO obterContatoFuncionario(Funcionario funcionario);
}
