package br.com.acervofacil.domain.repository.funcionario;

/**
 * Esta classe será responsável por centralizar as consultas relacionadas
 * à entidade funcionário.
 */
public class FuncionarioRepositoryImpl {

    public static final String BUSCAR_FUNCIONARIO_CONTATO_ENDERECO_USUARIO =
            "SELECT f.id AS id, " +
                    "f.cpf AS cpf, " +
                    "f.nome AS nome, " +
                    "f.dataNascimento AS dataNascimento, " +
                    "f.cargo AS cargo, " +
                    "f.dataCriacao AS dataCriacao, " +
                    "f.dataAtualizacao AS dataAtualizacao, " +
                    "ct.id AS contatoId, " +
                    "e.id AS enderecoId, " +
                    "u.id AS usuarioId " +
                    "FROM Funcionario f " +
                    "JOIN f.contato ct " +
                    "JOIN f.endereco e " +
                    "LEFT JOIN f.usuario u";
}
