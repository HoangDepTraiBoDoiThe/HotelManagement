package com.example.hotelmanagement.Auth;

import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Configuration
public class AuthUtils {
    @Value("${auth.security.token.securityKey}")
    private String jwtKey;
    @Value("${auth.security.token.expirationInMinutes}")
    private int expirationInMinutes;
    private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);
    private SecretKey getSecurityKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecurityKey()).build().parse(token);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Invalid jwt token : {} ", e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Expired token : {} ", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("This token is not supported : {} ", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("No  claims found : {} ", e.getMessage());
        }
        return false;
    }

    public <R> R extractClaimProperty(String token, Function<Claims, R> func) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        return func.apply(claims);
    }

    public String generateToken(AuthUserDetail userDetail) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", userDetail.getEmail());
        claims.put("user name", userDetail.getUsername());
        claims.put("roles", userDetail.getAuthorities());
        claims.put("id", userDetail.getId());
        Instant now = Instant.now();
        Instant expiryDate = now.plus(expirationInMinutes, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiryDate))
                .signWith(getSecurityKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
