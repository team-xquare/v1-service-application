package io.github.v1serviceapplication.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "application 서비스 API 명세서",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "박상우",
                        email = "b01056721617@email.co.kr"
                )
        )
)
@Configuration
public class SwaggerConfig {
}