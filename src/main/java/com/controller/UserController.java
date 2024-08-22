package com.controller;

import com.ApiResponse.ApiResponse;
import com.model.User;
import com.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> addUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(200).body(userService.addUser(user));
    }

    public static class UserCredentials{
        String email;
        String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> verifyUserCredentials(@RequestBody UserCredentials credentials){
        return ResponseEntity.accepted().body(
                userService.verifyUserCredentials(credentials.getEmail(), credentials.getPassword())
        );
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUserToken(HttpServletRequest request){
        return ResponseEntity.status(200).body(userService.verifyUserToken(request));
    }

}
