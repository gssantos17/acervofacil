package br.com.acervofacil.dto.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenLibraryResultadoBuscaDTO {

    @JsonProperty("numFound")
    private Integer totalResultados;

    @JsonProperty("start")
    private Integer inicio;

    @JsonProperty("docs")
    private List<LivroResultadoDTO> livros;

    @Data
    public static class LivroResultadoDTO {

        private String title;

        private List<String> isbn;

        @JsonProperty("author_name")
        private List<String> autores;

        @JsonProperty("first_publish_year")
        private Integer anoPublicacao;

        @JsonProperty("publisher")
        private List<String> editoras;

        @JsonProperty("language")
        private List<String> idiomas;

        @JsonProperty("number_of_pages_median")
        private Integer numeroPaginas;

        @JsonProperty("cover_i")
        private Integer idCapa; // pode ser usado para montar a URL da imagem da capa
    }
}