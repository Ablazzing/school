package com.javaacademy.school.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        Info info = new Info()
                .title("Это api Школы № 1352")
                .contact(new Contact().email("developer@yandex.ru")
                        .name("Юрий Молодыко")
                        .url("www.java-academy.ru"))
                .description("Это публичное апи школы №1352. Здесь много нового!");
        return new OpenAPI()
                .info(info);
    }
}
