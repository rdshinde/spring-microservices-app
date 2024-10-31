package com.rdshinde.microservices.order_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI orderServiceApi(){
        return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info()
                .title("Order Service API")
                .description("Order Service API")
                .version("v1.0.0"));
    }
}