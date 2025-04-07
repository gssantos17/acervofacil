package br.com.acervofacil.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
        @NotBlank(message = "A rua não pode estar vazia.")
        @Size(max = 255, message = "A rua não pode ter mais de 255 caracteres.")
        String rua,

        @PositiveOrZero(message = "O número deve ser positivo ou zero.")
        int numero,

        @Size(max = 255, message = "O complemento não pode ter mais de 255 caracteres.")
        String complemento,

        @Size(max = 255, message = "O bairro não pode ter mais de 255 caracteres.")
        String bairro,

        @NotBlank(message = "A cidade não pode estar vazia.")
        @Size(max = 255, message = "A cidade não pode ter mais de 255 caracteres.")
        String cidade,

        @NotBlank(message = "O estado não pode estar vazio.")
        @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres.")
        String estado,

        @NotBlank(message = "O CEP não pode estar vazio.")
        @Pattern(regexp = "^[0-9]{8}$", message = "O CEP deve conter exatamente 8 dígitos numéricos.")
        String cep
) {}

