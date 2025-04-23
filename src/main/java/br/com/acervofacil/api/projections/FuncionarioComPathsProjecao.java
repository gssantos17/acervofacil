package br.com.acervofacil.api.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface FuncionarioComPathsProjecao {

    // Dados do funcionário
    UUID getId();
    String getCpf();
    String getNome();
    LocalDate getDataNascimento();
    String getCargo();

    // Ids dos recursos relacionados
    UUID getUsuarioId();
    UUID getContatoId();
    UUID getEnderecoId();

    // Caminhos (paths) para os recursos associados
    default String getUsuarioPath() {
        return getUsuarioId() != null ? "usuarios/" + getUsuarioId() : null;
    }

    default String getContatoPath() {
        return getContatoId() != null ? "contatos/" + getContatoId() : null;
    }

    default String getEnderecoPath() {
        return getEnderecoId() != null ? "enderecos/" + getEnderecoId() : null;
    }

    // Datas de criação e atualização
    LocalDateTime getDataCriacao();
    LocalDateTime getDataAtualizacao();
}
