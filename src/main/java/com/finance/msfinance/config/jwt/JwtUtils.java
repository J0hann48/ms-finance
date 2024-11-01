package com.finance.msfinance.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKeyString;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    //Crar un token de acceso
    public String generateAccesToken(String nameuser){
        return Jwts.builder()
                .subject(nameuser)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validar toke de acceso
     * @return
     */

    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e){
            log.error("Token invalid, error: ".concat(e.getMessage()));
            return false;
        }
    }

    /**
     * Obtener el username del token
     */

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Obtener un solo claim del token
     */

    public <T> T getClaim(String token, Function<Claims, T> claimsFunction){
        Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    /**
     * Obtener todos los claims del token
     */

    public Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }
}
