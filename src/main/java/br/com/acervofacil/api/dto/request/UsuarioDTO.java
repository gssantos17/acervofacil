package br.com.acervofacil.api.dto.request;

import br.com.acervofacil.domain.enums.Role;
import br.com.acervofacil.domain.validation.CPF;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        @NotBlank(message = "O CPF não pode estar vazio.")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
        @CPF(message = "CPF inválido.")
        String cpf,

        @NotBlank(message = "A senha não pode estar vazia.")
        @Size(max = 16, message = "A senha não pode ter mais de 16 caracteres.")
        String senha,

        @Enumerated(EnumType.STRING)
        Role role
) {}

