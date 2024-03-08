package com.isekai.ssgserver.jwt.service;

import com.isekai.ssgserver.jwt.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
     * @param accountId 유저 계정ID
     * @param role 유저 역할 (SSG: 통합회원 / SOCIAL: 소셜회원)
     * @return Access token, Refresh token (String)
     */
    public JwtToken createToken(String accountId, String role) {

        Claims claims = Jwts.claims().setSubject(accountId);  // subject 사용자 식별
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

    /**
     * 토큰 검증
     * @param token jwt token
     * @return true(유효) / false(유효하지 않음)
     */
    public boolean verifyToken(String token) {
        try {
            // 토큰 파싱하여 claims 추출
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            // 토큰이 유효한지 (만료시간) 검증 - t/f 반환
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            // 토큰 파싱 과정에서 어떠한 예외라도 발생한다면, 토큰 유효하지 않은 것으로 상정
            return false;
        }
    }

    /**
     * 토큰에 담겨있는 계정 id(로그인 입력 id) 획득. 변경 필요
     * @param token
     * @return 계정 id
     */
    public String getAccountId(String token) {
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
