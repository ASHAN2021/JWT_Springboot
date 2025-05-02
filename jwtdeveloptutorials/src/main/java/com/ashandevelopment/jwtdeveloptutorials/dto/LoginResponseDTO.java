package com.ashandevelopment.jwtdeveloptutorials.dto;

import java.time.LocalDateTime;

public class LoginResponseDTO {
    private String token;
    private LocalDateTime time;
    private String message;
    private String error;

    public LoginResponseDTO(String token, String message, LocalDateTime time, String error) {
        this.token = token;
        this.message = message;
        this.time = time;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
