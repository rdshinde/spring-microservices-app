package com.rdshinde.microservices.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI productServiceApi(){
        return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info()
                .title("Product Service API")
                .description("Product Service API")
                .version("v1.0.0"));
    }
}
