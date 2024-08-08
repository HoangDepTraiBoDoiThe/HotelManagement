package com.example.hotelmanagement.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.function.Function;

@Configuration
public class AuthUtils {
    @Value("{auth.security.token.securityKey}")
    private String jwtKey;
    
    public boolean isTokenExpired(String token) {
        return getClaimProperty(token, Claims::getExpiration).before(new Date());
    }

    public <R> R getClaimProperty(String token, Function<Claims, R> func) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        return func.apply(claims);
    }
}
