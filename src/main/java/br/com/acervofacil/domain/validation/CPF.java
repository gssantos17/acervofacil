package br.com.acervofacil.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER}) // A anotação pode ser usada em campos e parâmetros
@Retention(RetentionPolicy.RUNTIME) // A anotação será processada em tempo de execução
@Constraint(validatedBy = ValidadorCPF.class) // Classe que implementa a lógica de validação
public @interface CPF {
    String message() default "CPF inválido."; // Mensagem de erro padrão

    Class<?>[] groups() default {}; // Grupos de validação (opcional)

    Class<? extends Payload>[] payload() default {}; // Payload para contextualização (opcional)
}