package com.finance.msfinance.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKeyString;
    @Value("${jwt.time.expiration}")
    private long timeExpiration;

    /**
     * Obtener el username del token
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails);
    }


    //Crar un token de acceso
    public String createToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + timeExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKeyString)
                .compact();
    }

    /**
     * Validar toke de acceso
     * @return
     */

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String userEmail = extractUserEmail(token);
            return (userEmail.equalsIgnoreCase(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e){
            log.error("Token invalid, error: ".concat(e.getMessage()));
            return false;
        }

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Obtener todos los claims del token
     */

    public Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSignInKey())
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(secretKeyString);
        return Keys.hmacShaKeyFor(keyBites);
    }
}
