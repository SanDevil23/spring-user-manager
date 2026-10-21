package com.oms.user_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI userManagerOpenAPI() {
        final String securitySchemaName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("User Manager API")
                        .version("1.0.0")
                        .description("API documentation for the User Manager platform.")
                ).components(new Components()
                        .addSecuritySchemes(
                                securitySchemaName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemaName)
                );
    }
}
