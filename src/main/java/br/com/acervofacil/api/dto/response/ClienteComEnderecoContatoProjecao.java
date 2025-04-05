package br.com.acervofacil.api.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public interface ClienteComEnderecoContatoProjecao {

    // Informações do cliente
    UUID getId();
    String getCpf();
    String getNome();
    LocalDate getDataNascimento();
    Integer getQuantidadeEmprestimos();

    // Informações do contato (assumindo que há uma associação OneToOne entre Cliente e Contato)
    String getTelefoneCelular();
    String getTelefoneFixo();
    String getEmail();

    // Informações do endereço (assumindo que há uma associação OneToOne entre Cliente e Endereco)
    String getRua();
    int getNumero();
    String getComplemento();
    String getBairro();
    String getCidade();
    String getEstado();
    String getCep();
}
