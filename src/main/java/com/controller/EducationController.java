package com.controller;

import com.ApiResponse.ApiResponse;
import com.Helpers.DateUtils;
import com.Helpers.EducationType;
import com.manager.config.TokenService;
import com.model.*;
import com.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/education")
public class EducationController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    public static class UserEducationBody{
        private String educationType;
        private String institutionName;
        private String startDate;
        private String endDate;
        private String course;

        public String getEducationType() {
            return educationType;
        }

        public void setEducationType(String educationType) {
            this.educationType = educationType;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<UserEducation>>> addUserEducation(
            @RequestBody UserEducationBody userEducationBody,
            HttpServletRequest request, HttpServletResponse httpResponse)
    {
        ApiResponse<List<UserEducation>> response;
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            UserEducation education = new UserEducation(
                    EducationType.fromString(userEducationBody.getEducationType()),
                    userEducationBody.getInstitutionName(),
                    DateUtils.parseDate(userEducationBody.getStartDate()),
                    DateUtils.parseDate(userEducationBody.getEndDate()),
                    userEducationBody.getCourse()
            );
            response = userService.addUserEducation(email, education);
            int status = response.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(response);
        }
        catch(Exception e){
            System.out.println("Caught exception in addUserEducation controller : " + e.getMessage());
            return ResponseEntity.status(httpResponse.getStatus()).body(new ApiResponse<>(-1, "Failed to save education details.", null));
        }
    }

    // Update
    @PatchMapping("")
    public ResponseEntity<ApiResponse<List<UserEducation>>> updateUserSchoolEducation(
            @RequestBody String educationType,
            @RequestBody String institutionName,
            @RequestBody String startDate,
            @RequestBody String endDate,
            @RequestBody String course,
            HttpServletRequest request, HttpServletResponse httpResponse){
        ApiResponse<List<UserEducation>> response;
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            UserEducation education = new UserEducation(EducationType.fromString(educationType),
                    institutionName, DateUtils.parseDate(startDate), DateUtils.parseDate(endDate), course);
            response = userService.updateUserEducation(email, education);
            int status = response.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(response);
        }
        catch(Exception e){
            System.out.println("Caught exception in updateUserEducation controller : " + e.getMessage());
            return ResponseEntity.status(httpResponse.getStatus()).body(new ApiResponse<>(-1, "Failed to update education details.", null));
        }
    }

    // Delete
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<List<UserEducation>>> deleteUserEducation(
            @RequestBody UserEducationBody userEducationBody,
            HttpServletResponse response, HttpServletRequest request){
        try{
            String email = tokenService.getUserEmailFromHeader(request);

            UserEducation education = new UserEducation(EducationType.fromString(userEducationBody.getEducationType()),
                    userEducationBody.getInstitutionName(), DateUtils.parseDate(userEducationBody.getStartDate()),
                    DateUtils.parseDate(userEducationBody.getEndDate()), userEducationBody.getCourse());

            ApiResponse<List<UserEducation>> res = userService.deleteUserEducation(email, education);
            int status = res.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(res);
        }
        catch (Exception e){
            System.out.println("Caught exception in deleteUserEducation controller: " + e.getMessage());
            return ResponseEntity.status(response.getStatus()).body(new ApiResponse<>(-1, "Failed to delete education details.", null));
        }
    }

    // Get education list
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserEducation>>> getUserEducationList(HttpServletResponse response, HttpServletRequest request){
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            ApiResponse<List<UserEducation>> res = userService.getUserEducationList(email);
            int status = res.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(res);
        }
        catch (Exception e){
            System.out.println("Caught exception in getUserEducationList controller: " + e.getMessage());
            return ResponseEntity.status(response.getStatus()).body(new ApiResponse<>(-1, "Failed to fetched related education details.", null));
        }
    }
}
