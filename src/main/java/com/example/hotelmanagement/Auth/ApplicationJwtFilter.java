package com.example.hotelmanagement.Auth;

import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetail;
import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ApplicationJwtFilter extends OncePerRequestFilter {
    private final AuthUtils utils;
    private final AuthUserDetailService authUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtToken(request);
            if (!token.isBlank() && !utils.validateToken(token)) {
                AuthUserDetail authUserDetail = (AuthUserDetail) authUserDetailService.loadUserByUsername(utils.extractClaimProperty(token, Claims::getSubject));
                var nameAndPassAuth = new UsernamePasswordAuthenticationToken(authUserDetail, null, authUserDetail.getAuthorities());
                nameAndPassAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(nameAndPassAuth);
            }
        } catch (Exception exception) {
            logger.warn("Server message: Cannot set user authentication: ", exception.getCause());
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        String token = "";
        if (header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        return token;
    }
}
