package br.com.acervofacil.api.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContatoResponseDTO {
    private UUID id;
    private String telefoneCelular;
    private String telefoneFixo;
    private String email;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
