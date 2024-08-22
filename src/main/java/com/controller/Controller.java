package com.controller;

import com.ApiResponse.ApiResponse;
import com.model.User;
import com.service.MongoDb.UserMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
    @Autowired
    private UserMongoDbService userMongoDbService;


    @PostMapping("/api/auth/test")
    public ResponseEntity<ApiResponse<User>> enrollUser(@RequestBody User user){
        ApiResponse<User> res = userMongoDbService.saveUser(user);
        return ResponseEntity.status(201).body(res);
    }
}
