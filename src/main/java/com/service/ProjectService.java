package com.service;

import com.ApiResponse.ApiResponse;
import com.model.Project;
import com.repository.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public ApiResponse<Project> saveProject(boolean update, Project project){
        ApiResponse<Project> apiResponse = new ApiResponse<>();
        try{
            Project savedProject = projectRepository.save(project);
            apiResponse.setData(savedProject);
            apiResponse.setCode(1);
            apiResponse.setMessage("Project Saved");
        }
        catch (Exception e){
            System.out.println("Caught exception in com.service.ProjectService.saveProject() due to " + e.getMessage());
            if(e.getMessage().contains("project_user_unique dup key"))
                apiResponse.setMessage("A project with this title already exists in your list.");
            else
                apiResponse.setMessage("Failed to save project details.");
            apiResponse.setCode(-1);
            apiResponse.setData(null);
        }
        return apiResponse;
    }

    public ApiResponse<String> deleteProject(ObjectId id){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try{
            Project project = projectRepository.findById(id).orElse(null);
            if(project==null){
                apiResponse.setCode(0);
                apiResponse.setMessage("Project not found.");
            }
            else{
                projectRepository.delete(project);
                apiResponse.setCode(1);
                apiResponse.setMessage("Project deleted successfully.");
            }
        }
        catch (Exception e){
            System.out.println("Caught exception in com.service.ProjectService.deleteProject() due to " + e.getMessage());
            apiResponse.setMessage("An error occurred while trying to delete the project. Please try again later.");
            apiResponse.setCode(-1);
        }
        return apiResponse;
    }

    public ApiResponse<List<Project>> getProjectList(String email){
        ApiResponse<List<Project>> response = new ApiResponse<>();
        try{
            List<Project> projects = projectRepository.findByUserEmail(email);
            response.setData(projects);
            response.setCode(1);
            response.setMessage("Fetched all the projects.");
        }
        catch(Exception e){
            System.out.println("Caught exception in com.service.ProjectService.getProjectList() due to " + e.getMessage());
            response.setCode(-1);
            response.setMessage("Unexpected error occurred while getting projects list.");
        }
        return response;
    }
}
