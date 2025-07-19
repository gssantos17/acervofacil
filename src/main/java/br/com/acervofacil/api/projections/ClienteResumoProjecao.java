package br.com.acervofacil.api.projections;

import java.time.LocalDate;

public interface ClienteResumoProjecao {
    // Dados do Cliente
    String getNome();
    String getCpf();
    LocalDate getDataNascimento();

    // Dados de Contato
    String getTelefoneCelular();
    String getTelefoneFixo();
    String getEmail();

    // Dados de Endereço
    String getRua();
    Integer getNumero();
    String getComplemento();
    String getBairro();
    String getCidade();
    String getEstado();
    String getCep();
}
