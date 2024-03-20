package com.isekai.ssgserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);  // 서버 응답시 json을 자바스크립트에서 처리할 수 있음
		config.addAllowedOriginPattern("*");  // 모든 ip에 응답 허용
		config.addAllowedHeader("*");  // 모든 header 응답 허용
		config.addExposedHeader("*");  // 모든 응답 허용
		config.addAllowedMethod("*");  // 모든 요청 method 응답 허용
		source.registerCorsConfiguration("/api/**", config);

		return source;
	}
}
