package br.com.acervofacil.domain.service.funcionario;

import br.com.acervofacil.api.dto.request.FuncionarioDTO;
import br.com.acervofacil.api.dto.request.FuncionarioUpdateDTO;
import br.com.acervofacil.api.dto.response.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.dto.response.FuncionarioComPathsProjecao;
import br.com.acervofacil.api.dto.response.FuncionarioResponseDTO;
import br.com.acervofacil.api.dto.response.PaginacaoCustomizada;
import br.com.acervofacil.domain.entity.Funcionario;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioService {

    /**
     * Salva um novo funcionário no sistema.
     *
     * @param funcionarioDTO Objeto contendo os dados do funcionário.
     * @return FuncionarioResponseDTO representando o funcionário salvo.
     */
    FuncionarioResponseDTO salvar(FuncionarioDTO funcionarioDTO);

    /**
     * Atualiza um funcionário existente com base no ID informado.
     *
     * @param id             Identificador único do funcionário.
     * @param funcionarioDTO Objeto contendo os novos dados do funcionário.
     * @return FuncionarioResponseDTO atualizado.
     */
    FuncionarioResponseDTO atualizar(UUID id, FuncionarioUpdateDTO funcionarioDTO);

    /**
     * Remove um funcionário do sistema com base no ID informado.
     *
     * @param id Identificador único do funcionário.
     */
    void deletar(UUID id);

    /**
     * Busca a entidade Funcionario pelo identificador, que pode ser um UUID (ID) ou String (CPF).
     *
     * @param identificador ID (UUID) ou CPF (String).
     * @return Funcionario encontrado, se existir.
     */
    Funcionario buscarEntidade(Object identificador);

    /**
     * Busca um funcionário pelo seu identificador único (UUID).
     *
     * @param id Identificador único do funcionário.
     * @return FuncionarioResponseDTO, se encontrado.
     */
    FuncionarioResponseDTO buscarPorId(UUID id);

    /**
     * Busca um funcionário pelo CPF.
     *
     * @param cpf CPF do funcionário.
     * @return FuncionarioResponseDTO, se encontrado.
     */
    FuncionarioResponseDTO buscarPorCpf(String cpf);

    /**
     * Lista todos os funcionários de forma paginada.
     *
     * @param page Página atual.
     * @param size Tamanho da página.
     * @return Página contendo os funcionários encontrados.
     */
    PaginacaoCustomizada<FuncionarioComPathsProjecao> obterFuncionariosPaginados(int page, int size);
}
