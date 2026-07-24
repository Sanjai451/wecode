package com.AuthService.AuthService.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private boolean success;
    private String message;
    private LocalDateTime localDateTime;

}
