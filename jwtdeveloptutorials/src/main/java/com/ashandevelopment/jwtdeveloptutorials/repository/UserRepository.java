package com.ashandevelopment.jwtdeveloptutorials.repository;

import com.ashandevelopment.jwtdeveloptutorials.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
