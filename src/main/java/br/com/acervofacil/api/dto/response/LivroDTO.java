package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.domain.enums.StatusLivro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private UUID id;
    private String googleBooksId;
    private String titulo;
    private List<AutorResumoDTO> autores;
    private String isbn;
    private String descricao;
    private String editoraNome;
    private Integer anoPublicacao;
    private String idioma;
    private Integer numeroPaginas;
    private String capa;
    private String genero;
    private Integer quantidadeDisponivel;
    private Integer quantidadeTotal;
    private StatusLivro status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
