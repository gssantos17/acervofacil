package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.domain.enums.Role;
import br.com.acervofacil.domain.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private UUID id;
    private String cpf;
    private Role role;
    private StatusUsuario status;
    private LocalDateTime ultimoLogin;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
