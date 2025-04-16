package br.com.acervofacil.api.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * DTO de entrada para criação ou atualização de um livro.
 * Contém os dados essenciais para cadastrar ou atualizar um livro no sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroInputDTO {

    /**
     * ID único do livro no Google Books. Esse campo é obrigatório para identificar o livro.
     */
    @NotNull(message = "O ID do livro no Google Books é obrigatório.")
    private String googleBooksId;

    /**
     * ISBN do livro. Deve ser um número entre 10 e 13 caracteres.
     * Esse campo não é obrigatório, mas deve estar no formato correto se presente.
     */
    @Length(min = 10, max = 13, message = "O ISBN deve ter entre 10 e 13 caracteres.")
    private String isbn;

    /**
     * Quantidade disponível de exemplares do livro no sistema.
     * Esse campo é obrigatório e não pode ser negativo.
     */
    @NotNull(message = "A quantidade disponível é obrigatória.")
    @Min(value = 0, message = "A quantidade disponível não pode ser negativa.")
    private Integer quantidadeDisponivel;

    /**
     * Quantidade total de exemplares do livro no sistema.
     * Esse campo é obrigatório e não pode ser negativo.
     */
    @NotNull(message = "A quantidade total é obrigatória.")
    @Min(value = 0, message = "A quantidade total não pode ser negativa.")
    private Integer quantidadeTotal;

    public boolean existeISBN(){
        return isbn != null && !isbn.isEmpty();
    }

    public boolean existeGoogleID(){
        return !googleBooksId.isEmpty();
    }
}
