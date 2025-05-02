package com.ashandevelopment.jwtdeveloptutorials.service;

import com.ashandevelopment.jwtdeveloptutorials.dto.LoginRequestDTO;
import com.ashandevelopment.jwtdeveloptutorials.dto.LoginResponseDTO;
import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(UserEntity userData) {
        UserEntity newuser = new UserEntity(userData.getName(),userData.getEmail(),userData.getUsername(),passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(newuser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginData){
//        Boolean userPresent = isUserEnable(loginData.getUsername());
//        if(!userPresent){
//            return new LoginResponseDTO(null,"error",null,"User not found");
//        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(),loginData.getPassword()));
        } catch (Exception e) {
            return new LoginResponseDTO(null,"error",null,"User not found");
        }


        return new LoginResponseDTO("token", "token issued",LocalDateTime.now(),null);
    }

    private Boolean isUserEnable(String userName){
        return userRepository.findByUsername(userName).isPresent();
    }
}
