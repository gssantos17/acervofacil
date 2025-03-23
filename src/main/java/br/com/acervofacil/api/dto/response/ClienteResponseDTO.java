package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.api.dto.response.EnderecoResponseDTO;
import br.com.acervofacil.api.dto.response.UsuarioResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private UUID id;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private ContatoResponseDTO contato;
    private EnderecoResponseDTO endereco;
    private Integer quantidadeEmprestimos;
    private UsuarioResponseDTO usuario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
