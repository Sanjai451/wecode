package com.AuthService.AuthService.security;

import com.AuthService.AuthService.model.AuthUser;
import com.AuthService.AuthService.repository.AuthUserRepository;
import com.AuthService.AuthService.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthUserRepository authUserRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            AuthUserRepository authUserRepository
    ) {
        this.jwtService = jwtService;
        this.authUserRepository = authUserRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authorizationHeader.substring(7);

        try {
            String email = jwtService.extractEmail(token);

            if (email != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                AuthUser authUser = authUserRepository
                        .findByEmail(email)
                        .orElse(null);

                if (authUser != null
                        && jwtService.isTokenValid(
                        token,
                        authUser
                )) {

                    UserDetails userDetails = User.builder()
                            .username(authUser.getEmail())
                            .password(authUser.getPassword())
                            .roles(authUser.getRole().name())
                            .disabled(
                                    !Boolean.TRUE.equals(
                                            authUser.getEnabled()
                                    )
                            )
                            .build();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (JwtException | IllegalArgumentException exception) {
            // Invalid or expired token.
            // Do not authenticate the request.
        }

        filterChain.doFilter(request, response);
    }
}