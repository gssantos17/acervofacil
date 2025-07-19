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
                    "LEFT JOIN c.usuario u " +
                    "WHERE u.status = 'ATIVO'";

    public static final String BUSCAR_CLIENTE_CONTATO_ENDERECO_POR_NOME =
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
                    "LEFT JOIN c.usuario u " +
                    "WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', ?1, '%'))";

    public static final String BUSCAR_CLIENTE_RESUMO_POR_ID =
            "SELECT " +
                    "c.nome AS nome, " +
                    "c.cpf AS cpf, " +
                    "c.dataNascimento AS dataNascimento, " +
                    "ct.telefoneCelular AS telefoneCelular, " +
                    "ct.telefoneFixo AS telefoneFixo, " +
                    "ct.email AS email, " +
                    "e.rua AS rua, " +
                    "e.numero AS numero, " +
                    "e.complemento AS complemento, " +
                    "e.bairro AS bairro, " +
                    "e.cidade AS cidade, " +
                    "e.estado AS estado, " +
                    "e.cep AS cep " +
                    "FROM Cliente c " +
                    "JOIN c.contato ct " +
                    "JOIN c.endereco e " +
                    "WHERE c.id = ?1";

    public static final String BUSCAR_CLIENTE_RESUMO_POR_CPF =
            "SELECT " +
                    "c.nome AS nome, " +
                    "c.cpf AS cpf, " +
                    "c.dataNascimento AS dataNascimento, " +
                    "ct.telefoneCelular AS telefoneCelular, " +
                    "ct.telefoneFixo AS telefoneFixo, " +
                    "ct.email AS email, " +
                    "e.rua AS rua, " +
                    "e.numero AS numero, " +
                    "e.complemento AS complemento, " +
                    "e.bairro AS bairro, " +
                    "e.cidade AS cidade, " +
                    "e.estado AS estado, " +
                    "e.cep AS cep " +
                    "FROM Cliente c " +
                    "JOIN c.contato ct " +
                    "JOIN c.endereco e " +
                    "WHERE c.cpf = ?1";



}
