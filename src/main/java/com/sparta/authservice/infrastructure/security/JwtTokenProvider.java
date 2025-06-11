package com.sparta.authservice.infrastructure.security;

import com.sparta.authservice.domain.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_EXPIRATION=1000*60*30; // 30분
    private final String PREFIX_BEARER="Bearer ";
    private final String AUTHORIZATION_ROLE="role";
    public static final String AUTHORIZATION_HEADER="Authorization";

    public JwtTokenProvider(@Value("${security.jwt.secret}")
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


    public String getAUTHORIZATION_HEADER() {
        return AUTHORIZATION_HEADER;
    }
}