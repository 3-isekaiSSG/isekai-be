package com.isekai.ssgserver.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.exception.dto.ErrorDto;
import com.isekai.ssgserver.jwt.dto.AuthDto;
import com.isekai.ssgserver.jwt.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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
        String token = request.getHeader("Authorization");

        Authentication auth = null; //인증 정보를 저장

        if(token != null) {
            if (!jwtProvider.verifyToken(token)) {
                // todo Jwt 검증 로직 추가 필요 (Refresh Token 구현 후)
            }
            //token 으로 id 읽어오기
            String accountId = jwtProvider.getAccountId(token);
            String role = jwtProvider.getValue(token, "role");


            //role 에 따라 다르게 authentication 주기
            if ("SSG".equals(role)) {
                auth = getAuthentication(accountId, "SSG");
            } else if ("SOCIAL".equals(role)) {
                auth = getAuthentication(accountId, "SOCIAL");
            }
            else { //예외처리
                setErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
                return;
            }
            //securityContext 에 auth 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
            }
        // request, response를 필터 체인의 다음 필터로 넘김
        filterChain.doFilter(request, response);
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
