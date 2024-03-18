package com.isekai.ssgserver.jwt.service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.jwt.dto.JwtToken;
import com.isekai.ssgserver.redis.RedisService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtProvider {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.token.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

    private final RedisService redisService;

    /**
     * secret key (서명키) 생성
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

    }

    /**
     * 토큰 생성
     * role 유저 역할 (SSG: 통합회원 / SOCIAL: 소셜회원)
     * @param uuid 유저 uuid
     * @return Access token, Refresh token (String)
     */
    public JwtToken createToken(String uuid) {

        Claims claims = Jwts.claims().setSubject(uuid);  // subject 사용자 식별(uuid)
// todo uuid로 role 가져오기  & role에 따라 다른 jwt 조건 설정 ??
        String role = "SSG";
        claims.put("role", role);

        Date now = new Date();
        JwtToken tokenInfo = null;

        tokenInfo = new JwtToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime()+accessExpireTime))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime()+refreshExpireTime))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());

        // Reids DB에 {uuid: refresh token} 정보 저장
        redisService.saveRefreshToken(uuid, tokenInfo.getRefreshToken());

        return tokenInfo;
    }

    public JwtToken reissueToken(String accessToken, String refreshToken) {

        // 우리 서버에서 발급해준 jwt만 재발급해주겠다!!!
        // 보낸 access token의 서명키 확인
        try {
                verifyToken(accessToken);
        } catch (CustomException e) {
            System.out.println(e.getErrorCode().getMessage());
            if (e.getErrorCode().getMessage().equals("접근 권한이 없습니다.")) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
        }

        // uuid로 redis에 저장된 refresh token - 클라이언트 쿠키의 refresh token 대조
        String uuid = getUuid(accessToken);
        if (refreshToken == null || !redisService.getRefreshToken(uuid).equals(refreshToken)) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }
        // 기존 Redis 데이터 삭제, 신규 생성
        redisService.deleteRefreshToken(uuid);
        JwtToken newTokenInfo = createToken(uuid);
        return newTokenInfo;
    }

    /**
     * 토큰 검증
     * @param token jwt token
     * @return true(유효) / false(유효하지 않음)
     */
    public boolean verifyToken(String token) {
        try {
            // 토큰 파싱
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("검증성공");
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("만료됨");
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            // 토큰 파싱 과정에서 어떠한 예외라도 발생한다면, 토큰 유효하지 않은 것으로 상정
            System.out.println("예외발생");
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }
    }


    /**
     * 토큰에 담겨있는 계정 id(로그인 입력 id) 획득. 변경 필요
     * @param token
     * @return 계정 id
     */
    public String getUuid(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * jwt claim에 담긴 key-value 반환
     * @param token
     * @param key token claim에 설정되어 있는 key값 이름
     * @return 해당 key 있으면 value 반환 / 없으면 빈 문자열 "" 반환
     */
    public String getValue(String token, String key) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        if(claims.containsKey(key)) {
            return claims.get(key).toString();
        } else {
            return "";
        }
    }
}
