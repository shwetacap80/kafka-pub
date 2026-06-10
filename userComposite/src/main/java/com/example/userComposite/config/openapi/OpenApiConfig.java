package com.example.userComposite.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "User APIs",
                version = "v1",
                description = "API documentation for User platform",
                license = @License(
                        name = "Internal Use"
                )
        )
)
public class OpenApiConfig {
}