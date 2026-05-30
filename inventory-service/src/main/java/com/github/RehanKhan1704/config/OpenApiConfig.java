package com.github.RehanKhan1704.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI inventoryServiceApi(){
        return new OpenAPI()
            .info(
                new Info()
                    .title("Inventory Service")
                    .description("Invenotry MAnagement Service for Distrocart")
                    .version("v1.0.0")
                    .contact(
                        new Contact()
                            .name("Rehan Khan")
                            .email("rehanmkhan17@gmail.com")
                        )
                    .license(
                        new License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                    )
            )
            .externalDocs(
                new ExternalDocumentation()
                    .description("Github Repository")
                    .url("https://github.com/RehanKhan1704")
            );
    }
}
