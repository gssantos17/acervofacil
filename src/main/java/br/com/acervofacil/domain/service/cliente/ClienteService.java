package br.com.acervofacil.domain.service.cliente;

import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.response.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.api.dto.request.ClienteDTO;

import java.util.Optional;
import java.util.UUID;

public interface ClienteService {

    /**
     * Salva um novo cliente no sistema.
     *
     * @param clienteDTO Objeto contendo os dados do cliente.
     * @return ClienteDTO representando o cliente salvo.
     */
    ClienteResponseDTO salvar(ClienteDTO clienteDTO);

    /**
     * Atualiza um cliente existente com base no ID informado.
     *
     * @param id         Identificador único do cliente.
     * @param ClienteUpdateDTO Objeto contendo os novos dados do cliente.
     * @return ClienteResponseDTO atualizado.
     */
    ClienteResponseDTO atualizar(UUID id, ClienteUpdateDTO clienteDTO);

    /**
     * Remove um cliente do sistema com base no ID informado.
     *
     * @param id Identificador único do cliente.
     */
    void deletar(UUID id);

    /**
     * Busca a entidade Cliente pelo identificador, que pode ser um UUID (ID) ou String (CPF).
     *
     * @param identificador ID (UUID) ou CPF (String).
     * @return Cliente encontrado, se existir.
     */
    Cliente buscarEntidade(Object identificador);

    /**
     * Busca um cliente pelo seu identificador único (UUID).
     *
     * @param id Identificador único do cliente.
     * @return ClienteResponseDTO, se encontrado.
     */
    ClienteResponseDTO buscarPorId(UUID id);

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente.
     * @return ClienteResponseDTO, se encontrado.
     */
    ClienteResponseDTO buscarPorCpf(String cpf);

    /**
     * Lista todos os clientes de forma paginada.
     *
     * @param size Objeto de paginação e ordenação.
     * @return Página contendo os clientes encontrados.
     */
    PaginacaoCustomizada<ClienteComEnderecoContatoProjecao> obterClientesPaginados(int page, int size);
}
