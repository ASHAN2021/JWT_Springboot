package com.ashandevelopment.jwtdeveloptutorials.controller;

import com.ashandevelopment.jwtdeveloptutorials.dto.ProfileDTO;
import com.ashandevelopment.jwtdeveloptutorials.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {

    private  final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable String userId) {
        ProfileDTO profile = profileService.getProfileByUserId(userId);

        if(profile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user id"));
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createProfile(@PathVariable String userId, @RequestBody ProfileDTO profileDTO) {
        ProfileDTO profile = profileService.createProfile(userId, profileDTO);
        if(profile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user id"));
        }
        return ResponseEntity.ok(profile);
    }
}
