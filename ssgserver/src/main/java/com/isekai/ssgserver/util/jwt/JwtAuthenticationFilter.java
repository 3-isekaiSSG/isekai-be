package com.isekai.ssgserver.util.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.exception.dto.ErrorDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	// OncePerRequestFilter : 상속하는 대상을 필터로 등록될 수 있게 만들어주고, filter 동작 수행할 수 있도록 해줌
	private final JwtProvider jwtProvider;

	/**
	 * http 요청 header 의 토큰을 추출
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		System.out.println("filter 시작");
		try {
			String token = request.getHeader("Authorization");
			Authentication auth = null; //인증 정보를 저장
			if (token == null) {
				throw new CustomException(ErrorCode.NO_AUTHORITY);
			}

			if (jwtProvider.verifyToken(token)) {
				System.out.println("받은 token : " + token);

				//token 으로 id 읽어오기
				String uuid = jwtProvider.getUuid(token);
				String role = jwtProvider.getValue(token, "role");
				System.out.println("uuid:" + uuid);  // test
				System.out.println("role:" + role);   // test

				//role 에 따라 다르게 authentication 주기
				if ("SSG".equals(role)) {
					auth = getAuthentication(uuid, "SSG");
				} else if ("SOCIAL".equals(role)) {
					auth = getAuthentication(uuid, "SOCIAL");
				} else { //예외처리
					setErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
					return;
				}
				//securityContext 에 auth 정보 저장
				SecurityContextHolder.getContext().setAuthentication(auth);
				filterChain.doFilter(request, response);
			}
		} catch (CustomException e) {
			/* - verify token 과정에서 exception 발생한 경우 해당 내용 response
			 *  1. 유효하지만, 인증 기간이 만료된 경우
			 *  2. 토큰 자체가 유효하지 않은 경우*/
			response.setStatus(e.getErrorCode().getStatus());
			response.getWriter().write("message: " + e.getErrorCode().getMessage());
		}

	}

	private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(errorCode.getStatus());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ErrorDto errorResponse = new ErrorDto(errorCode.getStatus(), errorCode.getMessage());
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Authentication getAuthentication(String id, String role) {
		AuthDto authDto = AuthDto.builder()
			.id(id)
			.build();
		return new UsernamePasswordAuthenticationToken(authDto, "", List.of(new SimpleGrantedAuthority(role)));
	}

}
