package com.controller;

import com.ApiResponse.ApiResponse;
import com.Helpers.ProjectStatus;
import com.Helpers.VisibilityType;
import com.manager.config.TokenService;
import com.model.Project;
import com.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Project>> addProject(@RequestBody @Valid Project project, BindingResult bindingResult, HttpServletRequest request){
        try{
            if(bindingResult.hasErrors()){
                throw new RuntimeException(bindingResult.toString());
            }
            project.setUserEmail(tokenService.getUserEmailFromHeader(request));
            project.setVisibilityType(VisibilityType.fromString(project.getVisibility()));
            project.setProjectStatus(ProjectStatus.fromString(project.getStatus()));
            ApiResponse<Project> apiResponse = projectService.saveProject(false, project);
            int status = apiResponse.getCode() == 1 ? 201 : 400;
            return ResponseEntity.status(status).body(apiResponse);
        }
        catch (RuntimeException e){
            StringBuilder responseMessage = new StringBuilder();
            for(var rr: bindingResult.getFieldErrors()){
                // System.out.println(rr.getField() + ": " + rr.getDefaultMessage());
                responseMessage.append(rr.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>(-1, String.valueOf(responseMessage), null));
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.ProjectController.addProject() due to " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, "Failed to save project", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProject(@PathVariable String id){
        try{
            if(id.isBlank())
                return ResponseEntity.badRequest().body(new ApiResponse<>(0, "Invalid project to delete", null));
            ApiResponse<String> apiResponse = projectService.deleteProject(new ObjectId(id));
            int status = apiResponse.getCode() >= 0 ? 200 : 400;
            return ResponseEntity.status(status).body(apiResponse);
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.ProjectController.deleteProject() due to " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, "An error occurred while trying to delete the project. Please try again later.", null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Project>> updateProject(@PathVariable String id, @RequestBody Project project, BindingResult bindingResult, HttpServletRequest request){
        try{
            if (id.isBlank())
                return ResponseEntity.badRequest().body(new ApiResponse<>(0, "Invalid project ID.", null));
            if(bindingResult.hasErrors()){
                throw new RuntimeException(bindingResult.toString());
            }
            project.setId(new ObjectId(id));
            project.setUserEmail(tokenService.getUserEmailFromHeader(request));
            project.setVisibilityType(VisibilityType.fromString(project.getVisibility()));
            project.setProjectStatus(ProjectStatus.fromString(project.getStatus()));
            ApiResponse<Project> apiResponse = projectService.saveProject(true, project);
            int status = apiResponse.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(apiResponse);
        }
        catch (RuntimeException e){
            StringBuilder responseMessage = new StringBuilder();
            for(var rr: bindingResult.getFieldErrors()){
                responseMessage.append(rr.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>(-1, String.valueOf(responseMessage), null));
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.ProjectController.updateProject() due to " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, "Failed to update project", null));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Project>>> getProjectsList(HttpServletRequest request, HttpServletResponse response){
        try{
            String email = tokenService.getUserEmailFromHeader(request);
            ApiResponse<List<Project>> apiResponse = projectService.getProjectList(email);
            int status = apiResponse.getCode() == 1 ? 200 : 400;
            return ResponseEntity.status(status).body(apiResponse);
        }
        catch (Exception e){
            System.out.println("Caught exception in com.controller.ProjectController.getProjectsList() due to " + e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse<>(-1, "Failed to get user projects.", null));
        }
    }
}
