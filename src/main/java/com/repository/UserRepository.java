package com.repository;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
}
