package com.example.contacts_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI contactsOpenApi() {
        return new OpenAPI().info(new Info().title("Contacts API").version("v1")
                .description("REST API for creating and managing contacts."));
    }
}
