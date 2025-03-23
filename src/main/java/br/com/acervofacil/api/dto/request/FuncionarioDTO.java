package br.com.acervofacil.api.dto.request;

import br.com.acervofacil.domain.validation.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record FuncionarioDTO(
        @NotBlank(message = "O CPF não pode estar vazio.")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
        @CPF
        String cpf,

        @NotBlank(message = "O nome não pode estar vazio.")
        @Size(min = 5, max = 50, message = "O nome deve conter entre 5 e 50 caracteres.")
        String nome,

        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataNascimento,

        @NotBlank(message = "O cargo não pode estar vazio.")
        @Size(min = 3, max = 50, message = "O cargo deve conter entre 3 e 50 caracteres.")
        String cargo,

        @NotNull(message = "O contato não pode ser nulo.")
        ContatoDTO contato,

        @NotNull(message = "O endereço não pode ser nulo.")
        EnderecoDTO endereco,

        @NotNull(message = "O usuário não pode ser nulo.")
        UsuarioDTO usuario
) {}

