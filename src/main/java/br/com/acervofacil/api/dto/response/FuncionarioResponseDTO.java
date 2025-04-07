package br.com.acervofacil.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDTO {
    private UUID id;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String contatoPath;
    private String enderecoPath;
    private String usuarioPath;
    private String cargo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
