package com.school.matmassig.orchestrator.config;

import com.school.matmassig.orchestrator.util.JwtUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        System.out.println("Token received: " + token);

        try {
            String email = jwtUtils.getEmailFromToken(token);
            System.out.println("Valid token. Email: " + email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
