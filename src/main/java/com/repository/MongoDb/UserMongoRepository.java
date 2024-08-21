package com.repository.MongoDb;

import com.model.MongoDb.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserMongoRepository extends MongoRepository<User, Long> {
}
