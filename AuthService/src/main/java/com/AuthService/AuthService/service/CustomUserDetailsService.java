package com.AuthService.AuthService.service;

import com.AuthService.AuthService.model.AuthUser;
import com.AuthService.AuthService.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public CustomUserDetailsService(
            AuthUserRepository authUserRepository
    ) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email
                        )
                );

        return User.builder()
                .username(authUser.getEmail())
                .password(authUser.getPassword())
                .roles(authUser.getRole().name())
                .disabled(!Boolean.TRUE.equals(authUser.getEnabled()))
                .accountLocked(
                        Boolean.TRUE.equals(
                                authUser.getAccountLocked()
                        )
                )
                .build();
    }
}