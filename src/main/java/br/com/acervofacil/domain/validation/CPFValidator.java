package br.com.acervofacil.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return false; // CPF não pode ser nulo ou vazio
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex.: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Cálculo dos dígitos verificadores
        int[] pesosPrimeiroDigito = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String cpfSemDigitos = cpf.substring(0, 9);
        int primeiroDigito = calcularDigitoVerificador(cpfSemDigitos, pesosPrimeiroDigito);
        int segundoDigito = calcularDigitoVerificador(cpfSemDigitos + primeiroDigito, pesosSegundoDigito);

        // Verifica se os dígitos calculados coincidem com os dígitos informados
        return cpf.equals(cpfSemDigitos + primeiroDigito + segundoDigito);
    }

    private int calcularDigitoVerificador(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}