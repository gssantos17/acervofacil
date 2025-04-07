package br.com.acervofacil.domain.repository.cliente;

/**
 * Esta classe será responsável por centralizar as consultas relacionadas
 * à entidade cliente.
 */
public class ClienteRepositoryImpl {

    public static final String BUSCAR_CLIENTE_CONTATO_ENDERECO =
            "SELECT c.id AS id, " +
                    "c.cpf AS cpf, " +
                    "c.nome AS nome, " +
                    "c.dataNascimento AS dataNascimento, " +
                    "c.quantidadeEmprestimos AS quantidadeEmprestimos, " +
                    "ct.id AS contatoId, " +
                    "e.id AS enderecoId, " +
                    "u.id AS usuarioId " +
                    "FROM Cliente c " +
                    "JOIN c.contato ct " +
                    "JOIN c.endereco e " +
                    "LEFT JOIN c.usuario u";
}
