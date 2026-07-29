package com.thghpi.bandapp.band_api.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Central configuration of OpenAPI and swaggerUI for the API
 * OpenApiConfiguration a class that define a OpenAPI bean used throughout the API 
 */
@Configuration
public class OpenApiConfiguration {
    /** Field to define the security scheme name to be used */
    private final String securitySchemeName = "bearerAuth";

    /**
     * A bean to use for the configuration of the OpenAPI data of band-api and the swagger UI
     * @return an OpenAPI entity for configuration of the openAPI and swagger UI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("BandApp API")
                    .version("0.1.0")
                    .description("API for managing an orchestra"))
            .components(new Components().addSecuritySchemes(
                securitySchemeName,
                new SecurityScheme().name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )).addSecurityItem(
                new SecurityRequirement().addList("bearerAuth")
            );
    }
}
