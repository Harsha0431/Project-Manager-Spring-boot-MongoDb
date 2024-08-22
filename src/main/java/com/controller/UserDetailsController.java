package com.controller;

import com.ApiResponse.ApiResponse;
import com.manager.config.TokenService;
import com.model.*;
import com.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/details")
@Validated
public class UserDetailsController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserDetails>> updateUserDetails(@Valid @RequestBody UserDetails details, HttpServletRequest request, HttpServletResponse res) {
        ApiResponse<UserDetails> response;
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            response = userService.addOrUpdateUserDetails(email, details);
            int status = response.getCode() == 1? 200 : 400;
            return ResponseEntity.status(status).body(response);
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.updateUserDetails() due to: " + e.getMessage());
            return ResponseEntity.status(res.getStatus()).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<User>> getUserDetails(HttpServletRequest request, HttpServletResponse response){
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            ApiResponse<User> res = userService.getUserInformation(email);
            int status = res.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(res);
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.getUserDetails() due to: " + e.getMessage());
            return ResponseEntity
                    .status(response.getStatus())
                    .body(new ApiResponse<>(-1, "Failed to get user details.", null));
        }
    }
}
