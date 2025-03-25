package br.com.acervofacil.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteUpdateDTO(
        @NotBlank(message = "O Nome não pode estar vazio.")
        @Size(min = 5, max = 50, message = "O nome deve conter entre 5 e 50 caracteres.")
        String nome,
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataNascimento,
        ContatoDTO contato,
        EnderecoDTO endereco) {
}

