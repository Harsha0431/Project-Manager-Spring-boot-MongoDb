package com.service;

import com.ApiResponse.ApiResponse;
import com.manager.config.TokenService;
import com.model.User;
import com.model.UserDetails;
import com.model.UserEducation;
import com.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public ApiResponse<String> addUser(User user){
        try{
            User savedObject = userRepository.insert(user);
            return new ApiResponse<>(1, "User created successfully", null);
        }
        catch(Exception e){
            if(e.getMessage().contains("email dup key"))
                return new ApiResponse<>(0, "The email address is already registered. Please use a different email.", null);
            if(e.getMessage().contains("education_unique_index"))
                return new ApiResponse<>(0, "Username already exists", null);
            System.out.println("Caught exception in com.service.UserService.addUser() due to: " + e.getMessage());
            return new ApiResponse<>(-1, "Failed to register user", null);
        }
    }

    public ResponseEntity<ApiResponse<Map<String, String>>> verifyUserCredentials(String email, String password){
        try{
            if(email == null)
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(0, "Please enter valid email.", null)
                );
            if(password == null)
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(0, "Please enter valid password.", null)
                );
            User user = userRepository.findByEmail(email).orElse(null);
            if(user == null){
                return ResponseEntity.status(404).body(
                        new ApiResponse<>(0, "User with email " + email.strip() + " not found.", null)
                );
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(password, user.getPassword())){
                return ResponseEntity.badRequest().body(new ApiResponse<>(0, "Invalid password", null));
            }
            String token = tokenService.generateJwtToken(user.getEmail(), user.getUsername());
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());
            return ResponseEntity.ok().body(new ApiResponse<>(1, "Login successful", data));
        }
        catch (Exception e){
            System.out.println("Caught exception in com.service.UserService.verifyUserCredentials() service: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, "Failed to verify user credentials", null));
        }
    }

    public ResponseEntity<ApiResponse<Map<String, String>>> verifyUserToken(HttpServletRequest request){
        try{
            String token = tokenService.getTokenFromRequest(request);
            if(token!=null && !token.isEmpty()){
                boolean isTokenValid = tokenService.isTokenValid(token);
                if(isTokenValid) {
                    Map<String, String> payload = tokenService.getPayloadFromToken(token);
                    return ResponseEntity.ok().body(new ApiResponse<>(1, "Login successful.", payload));
                }
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>(-2, "Invalid token or no token provided. Please login to continue", null));
        }
        catch (Exception e){
            System.out.println("Caught exception in com.service.UserService.verifyUserToken() in UserService due to ~ " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-2, "Failed to verify user token", null));
        }
    }

    public User getUserObjectFromEmail(String email){
        try{
            return userRepository.findByEmail(email).orElse(null);
        }
        catch (Exception e){
            System.out.println("Caught exception in getUserObjectFromEmail from user service due to: " + e.getMessage());
            return null;
        }
    }

    // User details
    public ApiResponse<UserDetails> addOrUpdateUserDetails(String email, UserDetails details){
        ApiResponse<UserDetails> response = new ApiResponse<>();
        try{
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setUserDetails(details);
                User savedUser = userRepository.save(user);
                response.setData(savedUser.getUserDetails());
                response.setCode(1);
                response.setMessage("Details saved or updated successfully.");
            }
            else{
                throw new RuntimeException("User not found");
            }
        }
        catch (RuntimeException e){
            response.setMessage(e.getMessage());
            response.setCode(-1);
            response.setData(null);
        }
        catch(Exception e){
            System.out.println("Caught exception in addUserDetails service:" + e.getMessage());
            response.setCode(-1);
            response.setMessage("Failed to save user details");
            response.setData(null);
        }
        return response;
    }

    // CRUD operations on UserEducation
    public ApiResponse<List<UserEducation>> addUserEducation(String email, UserEducation education){
        ApiResponse<List<UserEducation>> response = new ApiResponse<>();
        try{
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                if(user.getEducationList()!=null && !user.getEducationList().isEmpty()) {
                    List<UserEducation> educationList = user.getEducationList();
                    boolean isDuplicate = false;
                    for(UserEducation userEducation: educationList){
                        if( userEducation.getInstitutionName().equalsIgnoreCase(education.getInstitutionName()) &&
                                userEducation.getEducationType().equals(education.getEducationType())
                        ){
                            if(userEducation.getCourse() == null && education.getCourse() == null) {
                                isDuplicate = true;
                                break;
                            }
                            else if(userEducation.getCourse()!=null && education.getCourse()!=null &&
                                    userEducation.getCourse().equalsIgnoreCase(education.getCourse())
                            ){
                                isDuplicate = true;
                                break;
                            }

                        }
                    }

                    if (isDuplicate) {
                        throw new RuntimeException("Duplicate education entry not allowed.");
                    }
                }
                if(user.getEducationList() == null){
                    user.setEducationList(new ArrayList<>());
                }
                user.getEducationList().add(education);
                User savedUser = userRepository.save(user);
                response.setCode(1);
                response.setMessage("Education details saved successfully.");
                response.setData(savedUser.getEducationList());
            }
            else
                throw new RuntimeException("User not found.");
        }
        catch (RuntimeException e){
            response.setData(null);
            response.setCode(0);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            System.out.println("Caught exception com.service.UserService.addUserEducation() due to: " + e.getMessage());
            response.setData(null);
            response.setCode(-1);
            response.setMessage("Failed to save education details.");
        }
        return response;
    }

    public ApiResponse<List<UserEducation>> updateUserEducation(String email, UserEducation updatedEducation){
        ApiResponse<List<UserEducation>> response = new ApiResponse<>();
        try{
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                List<UserEducation> educationList = user.getEducationList();
                for (int i = 0; i < educationList.size(); i++) {
                    UserEducation education = educationList.get(i);
                    if (education.getEducationType() == updatedEducation.getEducationType() &&
                        education.getInstitutionName().equalsIgnoreCase(updatedEducation.getInstitutionName()) &&
                        education.getCourse().equalsIgnoreCase(updatedEducation.getCourse()))
                    {
                        educationList.set(i, updatedEducation); // Update the matching education
                        break;
                    }
                }
                user.setEducationList(educationList);
                User savedUser = userRepository.save(user);
                response.setCode(1);
                response.setMessage("Education details updated successfully.");
                response.setData(savedUser.getEducationList());
            }
            else
                throw new RuntimeException("User not found.");
        }
        catch (RuntimeException e){
            response.setData(null);
            response.setCode(0);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            System.out.println("Caught exception com.service.UserService.updateUserEducation() due to: " + e.getMessage());
            response.setData(null);
            response.setCode(-1);
            response.setMessage("Failed to update education details.");
        }
        return response;
    }

    public ApiResponse<List<UserEducation>> deleteUserEducation(String email, UserEducation deleteEducation){
        ApiResponse<List<UserEducation>> response = new ApiResponse<>();
        try{
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                List<UserEducation> educationList = user.getEducationList();
                educationList.removeIf(
                        (education ->
                                education.getEducationType() == deleteEducation.getEducationType()
                                && education.getInstitutionName().equalsIgnoreCase(deleteEducation.getInstitutionName())
                                && (education.getCourse()==deleteEducation.getCourse() || education.getCourse().equalsIgnoreCase(deleteEducation.getCourse()))
                        )
                );
                user.setEducationList(educationList);
                User savedUser = userRepository.save(user);
                response.setCode(1);
                response.setMessage("Education details updated successfully.");
                response.setData(savedUser.getEducationList());
            }
            else
                throw new RuntimeException("User not found.");
        }
        catch (RuntimeException e){
            response.setData(null);
            response.setCode(0);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            System.out.println("Caught exception com.service.UserService.deleteUserEducation() due to: " + e.getMessage());
            response.setData(null);
            response.setCode(-1);
            response.setMessage("Failed to delete education details.");
        }
        return response;
    }

    public ApiResponse<List<UserEducation>> getUserEducationList(String email){
        try{
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                return new ApiResponse<>(1, "User education details successfully.", user.getEducationList());
            }
            throw new RuntimeException("User not found.");
        }
        catch (RuntimeException e){
            return new ApiResponse<>(0, e.getMessage(), null);
        }
        catch (Exception e){
            System.out.println("Caught exception com.service.UserService.getUserEducationList() due to: " + e.getMessage());
            return new ApiResponse<>(-1, "Failed to get user education details.", null);
        }
    }

    // Get user all information
    public ApiResponse<User> getUserInformation(String email){
        try{
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                return new ApiResponse<>(1, "User information fetched successfully.", user);
            }
            throw new RuntimeException("User not found.");
        }
        catch (RuntimeException e){
            return new ApiResponse<>(0, e.getMessage(), null);
        }
        catch (Exception e){
            System.out.println("Caught exception com.service.UserService.getUserInformation() due to: " + e.getMessage());
            return new ApiResponse<>(-1, "Failed to get user information.", null);
        }
    }

}
