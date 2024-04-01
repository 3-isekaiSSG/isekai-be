package com.isekai.ssgserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.isekai.ssgserver.util.jwt.JwtAuthenticationFilter;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final CorsConfig corsConfig;

	// Spring Security 검사 비활성화
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers(
				"/v3/api-docs/**",
				"/swagger-ui/**",
				"/swagger-resources/**",
				// jwt 토큰 재발급
				"/jwt/**",
				// 전체 허용
				"/**"
			);
	}

	// Http Security
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			// .addFilter(corsFilter)
			.cors().configurationSource(corsConfig.corsFilter())
			.and()
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.httpBasic().disable()
			.formLogin().disable()
			.authorizeRequests()
			.anyRequest().permitAll()  // 모든 요청에 모든 사용자 접근 허용(개발 편의를 위해 설정)
			//                .anyRequest().authenticated()
			.and()
			// jwt 인증 필터 추가
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
