package com.ashandevelopment.jwtdeveloptutorials.filter;

import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.repository.UserRepository;
import com.ashandevelopment.jwtdeveloptutorials.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
            String authorization = request.getHeader("Authorization");

            if(authorization == null  || !authorization.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }

            String jwt_token = authorization.split(" ")[1];
            String username = jwtService.getUsername(jwt_token);

            if(username == null){
                filterChain.doFilter(request,response);
                return;
            }

            UserEntity userData = userRepository.findByUsername(username).orElse(null);

            if(userData == null){
                filterChain.doFilter(request,response);
                return;
            }

            if(SecurityContextHolder.getContext().getAuthentication() != null){
                filterChain.doFilter(request,response);
                return;
            }

        UserDetails userDetails = User.builder()
                .username(userData.getUsername())
                .password(userData.getPassword())
                .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

            token.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContext().setAuthentication(token);
            System.out.println(jwt_token);
            filterChain.doFilter(request,response);
    }
}
