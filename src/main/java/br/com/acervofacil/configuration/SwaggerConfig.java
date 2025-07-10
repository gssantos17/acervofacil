package br.com.acervofacil.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi clienteApi() {
        return GroupedOpenApi.builder()
                .group("Clientes")
                .pathsToMatch("/api/v1/clientes/**")
                .build();
    }

    @Bean
    public GroupedOpenApi contatoApi() {
        return GroupedOpenApi.builder()
                .group("Contatos")
                .pathsToMatch("/api/v1/contatos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usuarioApi() {
        return GroupedOpenApi.builder()
                .group("Usuários")
                .pathsToMatch("/api/v1/usuarios/**")
                .build();
    }

    @Bean
    public GroupedOpenApi funcionarioApi() {
        return GroupedOpenApi.builder()
                .group("Funcionários")
                .pathsToMatch("/api/v1/funcionarios/**")
                .build();
    }

    @Bean
    public GroupedOpenApi livroApi() {
        return GroupedOpenApi.builder()
                .group("Livros")
                .pathsToMatch("/api/v1/livros/**")
                .build();
    }

        @Bean
    public GroupedOpenApi ReservaApi() {
        return GroupedOpenApi.builder()
                .group("Reservas")
                .pathsToMatch("/api/v1/reservas/**")
                .build();
    }

}