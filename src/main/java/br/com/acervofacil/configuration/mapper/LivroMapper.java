package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.request.RequisicaoLivroDTO;
import br.com.acervofacil.api.dto.response.AutorResumoDTO;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.api.dto.response.LivroDTO;
import br.com.acervofacil.domain.entity.Autor;
import br.com.acervofacil.domain.entity.Livro;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre DTOs e a entidade Livro.
 */
@Mapper(componentModel = "spring")
public interface LivroMapper {

    /**
     * Mapeia um LivroGoogleDTO para a entidade Livro.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(source = "googleBooksId", target = "googleBooksId")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "editoraNome", target = "editoraNome")
    @Mapping(source = "anoPublicacao", target = "anoPublicacao")
    @Mapping(source = "idioma", target = "idioma")
    @Mapping(source = "numeroPaginas", target = "numeroPaginas")
    @Mapping(source = "capa", target = "capa")
    @Mapping(source = "genero", target = "genero")
    @Mapping(target = "quantidadeDisponivel", ignore = true)
    @Mapping(target = "quantidadeTotal", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Livro toEntity(LivroGoogleDTO dto);

    /**
     * Mapeia o LivroInputDTO para a entidade Livro.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(target = "quantidadeDisponivel", source = "quantidadeDisponivel")
    @Mapping(target = "quantidadeTotal", source = "quantidadeTotal")
    @Mapping(target = "status", constant = "DISPONIVEL")
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Livro toEntity(RequisicaoLivroDTO dto);

    /**
     * Mapeia a entidade Livro para o DTO de resposta LivroDTO.
     */
    @Mapping(source = "autores", target = "autores")
    LivroDTO toDTO(Livro livro);

    /**
     * Converte um conjunto de autores em uma lista de AutorResumoDTO.
     */
    default List<AutorResumoDTO> mapAutores(Set<Autor> autores) {
        if (autores == null) return null;
        return autores.stream()
                .map(autor -> new AutorResumoDTO(autor.getId(), autor.getNome()))
                .collect(Collectors.toList());
    }
}