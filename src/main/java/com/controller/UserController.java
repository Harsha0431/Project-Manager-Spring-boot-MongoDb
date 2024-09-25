package com.controller;

import com.ApiResponse.ApiResponse;
import com.model.User;
import com.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> addUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()){
                throw new RuntimeException(bindingResult.toString());
            }
            ApiResponse<String> apiResponse = userService.addUser(user);
            int status = apiResponse.getCode() == 1 ? 200: 400;
            return ResponseEntity.status(status).body(apiResponse);
        }
        catch (RuntimeException e){
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setCode(-1);
            apiResponse.setMessage(e.getMessage());
            if(bindingResult.hasErrors()){
                StringBuilder responseMessage = new StringBuilder();
                for(var rr: bindingResult.getFieldErrors()){
                    responseMessage.append(rr.getDefaultMessage()).append(" ");
                }
                apiResponse.setMessage(String.valueOf(responseMessage));
            }
            return ResponseEntity.status(500).body(apiResponse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, e.getMessage(), null));
        }
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
        return userService.verifyUserCredentials(credentials.getEmail(), credentials.getPassword());
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Map<String, String>>> verifyUserToken(HttpServletRequest request){
        return userService.verifyUserToken(request);
    }

}
