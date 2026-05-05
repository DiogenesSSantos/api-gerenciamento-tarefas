package com.github.diogenesssantos.facilittecnologia.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                        new Info().title("api-gerenciamento-tarefas")
                                .description("api-rest criada para o desafio técnico - " +
                                        "Estágio em Desenvolvimento de Software " +
                                        "Facilit Tecnologia.")
                                .version("1.0.0")
                                .contact(new Contact().url("https://diogenesssantos.github.io/meu-portfolio/")
                                        .name("Diogenes S Santos").email("diogenescontatoofficial@hotmail.com"))
                                .summary("Gerenciador de tarefas")
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/DiogenesSSantos/api-gerenciamento-tarefas"));
    }


    @Bean
    public GroupedOpenApi whatsappControle() {
        return GroupedOpenApi.builder()
                .group("TarefaController")
                .pathsToMatch("/tarefas/**")
                .build();

    }


}
