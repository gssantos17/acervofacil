package br.com.acervofacil.api.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroGoogleDTO {

    private String googleBooksId; // ID do Google Books para o livro
    private String titulo; // Título do livro
    private List<String> autores; // Lista de autores
    private String isbn; // ISBN do livro
    private String descricao; // Descrição do livro
    private String editoraNome; // Nome da editora
    private Integer anoPublicacao; // Ano de publicação
    private String idioma; // Idioma do livro
    private Integer numeroPaginas; // Número de páginas
    private String capa; // URL da capa do livro
    private String genero; // Gênero do livro

}
