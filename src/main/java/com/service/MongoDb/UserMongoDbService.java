package com.service.MongoDb;

import com.ApiResponse.ApiResponse;
import com.model.MongoDb.User;
import com.repository.MongoDb.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMongoDbService {
    @Autowired
    private UserMongoRepository userMongoRepository;

    public ApiResponse<User> saveUser(User user){
        ApiResponse<User> response;
        try{
            User savedUser = userMongoRepository.save(user);
            response = new ApiResponse<>(1, "User saved successfully.", savedUser);
        }
        catch (Exception e){
            System.out.println("Caught exception in com.service.MongoDb.UserService due to: " + e.getMessage());
            response = new ApiResponse<>(-1, "Failed to save user to database.", null);
        }
        return response;
    }
}
