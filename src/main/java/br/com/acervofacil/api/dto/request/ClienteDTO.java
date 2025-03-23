package br.com.acervofacil.api.dto.request;

import br.com.acervofacil.domain.validation.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteDTO(
        @NotBlank(message = "O CPF não pode estar vazio.")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
        @CPF
        String cpf,

        @NotBlank(message = "O Nome não pode estar vazio.")
        @Size(min = 5, max = 50, message = "O nome deve conter entre 5 e 50 caracteres.")
        String nome,

        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataNascimento,

        @NotNull(message = "O contato não pode ser nulo.")
        ContatoDTO contato,

        @NotNull(message = "O endereço não pode ser nulo.")
        EnderecoDTO endereco,

        UsuarioDTO usuario
) {
}

