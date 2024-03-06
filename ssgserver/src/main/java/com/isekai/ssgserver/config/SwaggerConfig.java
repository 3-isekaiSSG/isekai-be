package com.isekai.ssgserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("isekai Project API Document")
                        .description("isekai sgg.com 클론코딩의 API 명세서입니다.")
                        .version("1.0")
                );
    }
}
