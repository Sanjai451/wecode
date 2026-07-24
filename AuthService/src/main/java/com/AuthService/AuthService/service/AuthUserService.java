package com.AuthService.AuthService.service;

import com.AuthService.AuthService.dtos.AuthResponse;
import com.AuthService.AuthService.dtos.MessageResponse;
import com.AuthService.AuthService.dtos.RegisteredRequestDto;
import com.AuthService.AuthService.dtos.RequestLoginDto;
import com.AuthService.AuthService.enums.Role;
import com.AuthService.AuthService.model.AuthUser;
import com.AuthService.AuthService.model.EmailVerificationToken;
import com.AuthService.AuthService.repository.AuthUserRepository;
import com.AuthService.AuthService.repository.EmailVerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final EmailService emailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public MessageResponse register(RegisteredRequestDto request){
        if(authUserRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email is already existed");
        }
        if(!request.getPassword().equals(request.getConfirm_password())){
            System.out.println(request.getPassword());
            System.out.println(request.getConfirm_password());
            throw new RuntimeException("passwords does not matches");
        }
        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.getEmail());
        authUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setRole(Role.USER);
        authUser.setEmailVerified(false);
        authUser.setEnabled(false);
        authUser.setAccountLocked(false);

        AuthUser savedUser = authUserRepository.save(authUser);

        String token = UUID.randomUUID().toString();
        System.out.println(token);

        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setAuthUserId(savedUser.getId());
        verificationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        emailVerificationTokenRepository.save(verificationToken);

        String verificationLink =
                "http://localhost:8080/auth/verify-email?token=" + token;

        emailService.sendVerificationEmail(
                savedUser.getEmail(),
                verificationLink
        );

        return new MessageResponse(
                true,
                "Registeration successfull , check your mail to verify account",
                LocalDateTime.now()
        );
    }

    public MessageResponse verifyEmail(String token){
        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(()->new RuntimeException("invalid verification token"));
        if(verificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token expired");
        }
        AuthUser authUser = authUserRepository
                .findById(verificationToken.getAuthUserId())
                .orElseThrow(()-> new RuntimeException("user not found with the specific token !"));

        authUser.setEmailVerified(true);
        authUser.setEnabled(true);

        authUserRepository.save(authUser);
        emailVerificationTokenRepository.delete(verificationToken);

        return new MessageResponse(
          true,
          "email verified now log in",
          LocalDateTime.now()
        );
    }

    public AuthResponse login(RequestLoginDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AuthUser authUser = authUserRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (!Boolean.TRUE.equals(authUser.getEmailVerified())) {
            throw new RuntimeException(
                    "Please verify your email before logging in"
            );
        }

        if (!Boolean.TRUE.equals(authUser.getEnabled())) {
            throw new RuntimeException(
                    "Your account is disabled"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(authUser);

        String refreshToken =
                jwtService.generateRefreshToken(authUser);

        return new AuthResponse(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getRole(),
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }
}
