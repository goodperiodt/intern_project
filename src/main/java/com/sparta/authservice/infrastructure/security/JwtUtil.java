package com.sparta.authservice.infrastructure.security;

import com.sparta.authservice.domain.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_EXPIRATION=1000*60*30; // 30분
    public static final String PREFIX_BEARER="Bearer ";
    private final String AUTHORIZATION_ROLE="role";
    public static final String AUTHORIZATION_HEADER="Authorization";

    public JwtUtil(@Value("${security.jwt.secret}")
                            String stringSecretKey) {
        byte[] decode = Base64.getDecoder().decode(stringSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(decode);
    }

    private String generateToken(String username,
                                Role role,
                                long expirationTime)
    {
        Date date = new Date();

        return Jwts.builder()
                .subject(username) // email
                .claim(AUTHORIZATION_ROLE, role.name())
                .expiration(new Date(date.getTime()+expirationTime))
                .issuedAt(date)
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(String username,
                                    Role role) {
        String token = generateToken(username, role, ACCESS_TOKEN_EXPIRATION);
        return PREFIX_BEARER+token;
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public UserInfo getUserInfo(String token) {
        String username = getUsername(token);
        String role = getUserRole(token);
        return new UserInfo(username, role);
    }

    private String getUsername(String token) {
        return getPayload(token).getSubject();
    }

    private String getUserRole(String token) {
        return getPayload(token).get("AUTHORIZATION_ROLE", String.class);
    }

    private Claims getPayload(String token) {
        return validateTokenAndGetClaims(token)
                .getPayload();
    }

    private Jws<Claims> validateTokenAndGetClaims(String token) {
        return jwtParser().parseSignedClaims(token);
    }

    private JwtParser jwtParser() {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build();
    }
}