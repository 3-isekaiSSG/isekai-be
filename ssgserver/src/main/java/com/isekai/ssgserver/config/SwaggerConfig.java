package com.isekai.ssgserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().addServersItem(new Server().url("/"))
			.components(new Components().addSecuritySchemes("basicScheme",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
			.info(new Info()
				.title("isekai Project API Document")
				.description("isekai sgg.com 클론코딩의 API 명세서입니다.")
				.version("1.0")
			);
	}
}
