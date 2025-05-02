package com.ashandevelopment.jwtdeveloptutorials.service;

import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;




public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;



    public MyUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;


    }


    //authenticate part
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username).orElse(null);

        if (userData == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }


        UserDetails user = User.builder()
                .username(userData.getUsername())
                .password(userData.getPassword())
                .build();
        return user;
    }
}
