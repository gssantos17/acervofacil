package br.com.acervofacil.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(userAgentInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor userAgentInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add("User-Agent", "AcervoFacil/1.0");
            return execution.execute(request, body);
        };
    }
}
