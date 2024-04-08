package com.isekai.ssgserver.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// config.addAllowedOriginPattern("*"); // 허용할 URL
		config.addAllowedOrigin("https://m.isekai-ssg.shop");
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedHeader("*"); // 허용할 Header
		config.addAllowedMethod("*"); // 허용할 Http Method
		config.setExposedHeaders(Arrays.asList("Authorization", "refreshToken"));
		source.registerCorsConfiguration("/**", config);
		// 모든 Url 대해 설정한 CorsConfiguration 등록
		return new CorsFilter(source);
	}

}
