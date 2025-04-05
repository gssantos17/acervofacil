package br.com.acervofacil.domain.service.endereco;

import br.com.acervofacil.api.dto.response.EnderecoResponseDTO;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;

import java.util.UUID;

public interface EnderecoService {
    EnderecoResponseDTO obterEnderecoID(UUID id);
    EnderecoResponseDTO obterEnderecoCliente(Cliente cliente);
    EnderecoResponseDTO obterEnderecoFuncionario(Funcionario funcionario);
}
