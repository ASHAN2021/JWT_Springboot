package com.ashandevelopment.jwtdeveloptutorials.controller;

import com.ashandevelopment.jwtdeveloptutorials.dto.LoginRequestDTO;
import com.ashandevelopment.jwtdeveloptutorials.dto.LoginResponseDTO;
import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return authService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginData){

        LoginResponseDTO res = authService.login(loginData);
        if(res.getError() != null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
