package com.ashandevelopment.jwtdeveloptutorials.service;

import com.ashandevelopment.jwtdeveloptutorials.dto.LoginRequestDTO;
import com.ashandevelopment.jwtdeveloptutorials.dto.LoginResponseDTO;
import com.ashandevelopment.jwtdeveloptutorials.dto.RegisterRequestDTO;
import com.ashandevelopment.jwtdeveloptutorials.dto.RegisterResponseDTO;
import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(RegisterRequestDTO userData) {
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

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("role","USER");
        claims.put("email","company@gmail.com");

        String token = jwtService.getJWTToken(loginData.getUsername(), claims);

        System.out.println(jwtService.getFieldFromToken(token,"role"));

        return new LoginResponseDTO(token, "token issued",LocalDateTime.now(),null);
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerData){
        if(isUserEnable(registerData.getUsername())){
            return new RegisterResponseDTO(null,"user already exists");
        }

        var userData = createUser(registerData);
        if(userData.getId() == null){
            return new RegisterResponseDTO(null,"System Error");
        }
        return new RegisterResponseDTO(String.format("user register at %s",userData.getId()),null);
    }

    private Boolean isUserEnable(String userName){
        return userRepository.findByUsername(userName).isPresent();
    }
}
