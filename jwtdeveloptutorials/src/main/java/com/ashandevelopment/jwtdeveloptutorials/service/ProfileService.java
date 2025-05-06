package com.ashandevelopment.jwtdeveloptutorials.service;

import com.ashandevelopment.jwtdeveloptutorials.dto.ProfileDTO;
import com.ashandevelopment.jwtdeveloptutorials.entity.ProfileEntity;
import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import com.ashandevelopment.jwtdeveloptutorials.repository.ProfileRepository;
import com.ashandevelopment.jwtdeveloptutorials.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileDTO getProfileByUserId(String userId) {
        ProfileEntity profile = profileRepository.findByUserId(userId).orElse(null);

        if (profile == null) {
            return null;
        }

        return new ProfileDTO(profile.getId(),profile.getStatus(),profile.getImage());
    }

    public ProfileDTO createProfile(String userId, ProfileDTO profileData) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        ProfileEntity profile = new ProfileEntity(profileData.getImage(),profileData.getStatus());
        profile.setUser(user);

        ProfileEntity savedProfile = profileRepository.save(profile);
        user.setProfile(savedProfile);

        userRepository.save(user);

        return new ProfileDTO(savedProfile.getId(),savedProfile.getStatus(),savedProfile.getImage());
    }
}
