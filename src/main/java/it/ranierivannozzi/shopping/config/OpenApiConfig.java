package it.ranierivannozzi.shopping.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Shopping Delivery API",
            version = "v1",
            description = "Shopping delivery API: orders, warehouses and optimized delivery routes"))
public class OpenApiConfig {}
