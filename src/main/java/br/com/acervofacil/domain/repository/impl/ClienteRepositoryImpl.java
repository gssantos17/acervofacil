package br.com.acervofacil.domain.repository.impl;

/**
 * Esta classe será responsável por centralizar as consultas relacionadas
 * a entidade cliente.
 */
public class ClienteRepositoryImpl {

    public static final String BUSCAR_CLIENTE_CONTATO_ENDERECO =
            "SELECT c.id AS id, c.cpf AS cpf, c.nome AS nome, c.dataNascimento AS dataNascimento, " +
                    "c.quantidadeEmprestimos AS quantidadeEmprestimos, " +
                    "ct.telefoneCelular AS telefoneCelular, ct.telefoneFixo AS telefoneFixo, ct.email AS email, " +
                    "e.rua AS rua, e.numero AS numero, e.complemento AS complemento, " +
                    "e.bairro AS bairro, e.cidade AS cidade, e.estado AS estado, e.cep AS cep " +
                    "FROM Cliente c " +
                    "JOIN c.endereco e " +
                    "JOIN c.contato ct ";
}
