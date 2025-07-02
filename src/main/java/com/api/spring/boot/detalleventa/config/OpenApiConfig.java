package com.api.spring.boot.detalleventa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Detalle de Ventas")
                .version("1.0")
                .description("API REST para gestionar detalles de ventas con soporte HATEOAS")
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("dev@empresa.com")
                    .url("http://localhost:8888"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de desarrollo"),
                new Server()
                    .url("http://localhost:8888")
                    .description("API Gateway")
            ));
    }
} 