package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.request.LivroInputDTO;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.domain.entity.Livro;
import org.mapstruct.*;

/**
 * Mapper para conversão entre DTOs e a entidade Livro.
 */
@Mapper(componentModel = "spring")
public interface LivroMapper {

    /**
     * Mapeia um LivroGoogleDTO para a entidade Livro.
     *
     * @param dto DTO com os dados do livro
     * @return A entidade Livro com os dados mapeados
     */
    @Mapping(target = "id", ignore = true) // ID é gerado automaticamente
    @Mapping(target = "autores", ignore = true) // será tratado separadamente
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
     *
     * @param dto DTO de entrada com os dados do livro
     * @return A entidade Livro com os dados mapeados
     */
    @Mapping(target = "id", ignore = true) // ID é gerado automaticamente
    @Mapping(target = "autores", ignore = true) // Autores devem ser tratados separadamente
    @Mapping(target = "quantidadeDisponivel", source = "quantidadeDisponivel") // Quantidade disponível
    @Mapping(target = "quantidadeTotal", source = "quantidadeTotal") // Quantidade total
    @Mapping(target = "status", constant = "DISPONIVEL") // Status padrão para o livro (DISPONIVEL)
    @Mapping(target = "dataCriacao", ignore = true) // A data de criação será gerada automaticamente
    @Mapping(target = "dataAtualizacao", ignore = true) // A data de atualização será gerada automaticamente
    Livro toEntity(LivroInputDTO dto);
}
