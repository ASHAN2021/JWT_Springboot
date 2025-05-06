package com.ashandevelopment.jwtdeveloptutorials.repository;

import com.ashandevelopment.jwtdeveloptutorials.entity.ProfileEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByUserId(String userId);
}
