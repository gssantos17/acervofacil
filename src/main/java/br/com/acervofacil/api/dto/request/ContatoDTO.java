package br.com.acervofacil.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContatoDTO(
        @NotBlank(message = "O telefone celular não pode estar vazio.")
        @Pattern(regexp = "^[0-9]{11}$", message = "O telefone celular deve conter 11 dígitos numéricos.")
        String telefoneCelular,

        @NotBlank(message = "O telefone fixo não pode estar vazio.")
        @Pattern(regexp = "^[0-9]{10}$", message = "O telefone fixo deve conter 10 dígitos numéricos.")
        String telefoneFixo,

        @NotBlank(message = "O email não pode estar vazio.")
        @Email(message = "O email deve ser válido.")
        @Size(max = 255, message = "O email não pode ter mais de 255 caracteres.")
        String email
) {}

