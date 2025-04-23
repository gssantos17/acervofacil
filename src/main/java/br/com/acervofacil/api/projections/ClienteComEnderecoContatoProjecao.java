package br.com.acervofacil.api.projections;

import java.time.LocalDate;
import java.util.UUID;

public interface ClienteComEnderecoContatoProjecao {

    UUID getId();
    String getCpf();
    String getNome();
    LocalDate getDataNascimento();
    Integer getQuantidadeEmprestimos();

    UUID getContatoId();
    UUID getEnderecoId();
    UUID getUsuarioId();

    default String getContatoPath() {
        return getContatoId() != null ? "contatos/" + getContatoId() : null;
    }

    default String getEnderecoPath() {
        return getEnderecoId() != null ? "enderecos/" + getEnderecoId() : null;
    }

    default String getUsuarioPath() {
        return getUsuarioId() != null ? "usuarios/" + getUsuarioId() : null;
    }
}