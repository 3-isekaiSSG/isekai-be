package com.isekai.ssgserver.jwt.service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.jwt.dto.JwtToken;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtProvider {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.token.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

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
// todo uuid로 role 가져오기 ??
        String role = "SSG";
        claims.put("role", role);

        Date now = new Date();
        JwtToken tokenInfo = null;
        if(role.equals("SSG")) {
            tokenInfo = new JwtToken(
                    Jwts.builder()
                            .setClaims(claims)
                            .setIssuedAt(now)
                            .setExpiration(new Date(now.getTime()+accessExpireTime))
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact()
//                    todo refresh token
//                    Jwts.builder()
//                            .setClaims(claims)
//                            .setIssuedAt(now)
//                            .setExpiration(new Date(now.getTime()+refreshExpireTime))
//                            .signWith(SignatureAlgorithm.HS256, secretKey)
//                            .compact()
            );

//            redisService.saveRefreshToken(tokenInfo.getAccessToken(), tokenInfo.getRefreshToken());
//            redisService.saveRefreshToken(tokenInfo.getAccessToken(),accountId);

        }
        if (role.equals("SOCIAL")) {
            tokenInfo = new JwtToken(
                    Jwts.builder()
                            .setClaims(claims)
                            .setIssuedAt(now)
                            .setExpiration(new Date(now.getTime()+accessExpireTime))
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact()
            );
        }

        return tokenInfo;
    }

    public JwtToken reissueToken(String token, String uuid) {

        // 보낸 access token의 서명키 확인
        try {
                verifyToken(token);
        } catch (CustomException e) {
            System.out.println(e.getErrorCode().getMessage());
            if (e.getErrorCode().getMessage().equals("접근 권한이 없습니다.")) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
        }
        // 우리 서버에서 발급해준 jwt만 재발급해주겠다!!!
        // todo uuid로 refresh token redis에서 가져오고 기존 것 지우기
        // refresh token 대조
        // create token

        JwtToken newAccessToken = createToken(uuid);
        // 새로운 refresh token redis 저장
        return newAccessToken;
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
