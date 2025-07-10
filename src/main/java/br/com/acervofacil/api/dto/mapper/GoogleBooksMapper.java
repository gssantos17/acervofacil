package br.com.acervofacil.api.dto.mapper;

import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoogleBooksMapper {

    // Livro
    @Mapping(source = "id", target = "googleBooksId")
    @Mapping(source = "volumeInfo.title", target = "titulo")
    @Mapping(source = "volumeInfo.authors", target = "autores")
    @Mapping(source = "volumeInfo.industryIdentifiers", target = "isbn", qualifiedByName = "mapIsbn")
    @Mapping(source = "volumeInfo.publisher", target = "editoraNome")
    @Mapping(source = "volumeInfo.publishedDate", target = "anoPublicacao", qualifiedByName = "mapAnoPublicacao")
    @Mapping(source = "volumeInfo.imageLinks.thumbnail", target = "capa")
    LivroGoogleDTO toLivroGoogleDTO(GoogleBooksResponse.Item item);

    List<LivroGoogleDTO> toLivroGoogleDTOList(List<GoogleBooksResponse.Item> items);

    // Métodos auxiliares
    @Named("mapIsbn")
    default String mapIsbn(List<GoogleBooksResponse.IndustryIdentifier> identifiers) {
        if (identifiers == null) return null;
        return identifiers.stream()
                .filter(id -> "ISBN_13".equalsIgnoreCase(id.getType()))
                .map(GoogleBooksResponse.IndustryIdentifier::getIdentifier)
                .findFirst()
                .orElse(null);
    }

    @Named("mapAnoPublicacao")
    default Integer mapAnoPublicacao(String publishedDate) {
        if (publishedDate == null || publishedDate.isBlank()) return null;
        try {
            return Integer.parseInt(publishedDate.substring(0, 4));
        } catch (Exception e) {
            return null;
        }
    }

    @Named("mapData")
    default LocalDate mapData(String date) {
        if (date == null || date.isBlank()) return null;
        try {
            if (date.matches("\\d{4}")) {
                return LocalDate.of(Integer.parseInt(date), 1, 1);
            } else if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            }
        } catch (Exception ignored) {}
        return null;
    }
}