package com.model;

import com.Helpers.ProjectStatus;
import com.Helpers.VisibilityType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "project")
public class Project {
    @Id
    private ObjectId id;
    @NotBlank
    @NotNull
    private ObjectId userId;
    @NotBlank
    @NotNull
    private String title;
    private String description;
    @NotBlank
    @NotNull
    private VisibilityType visibilityType;
    @NotBlank
    @NotNull
    private ProjectStatus projectStatus;
    private long likeCount;

    private List<MediaUploads> mediaUploads;
    private List<ProjectTech> techList;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VisibilityType getVisibilityType() {
        return visibilityType;
    }

    public void setVisibilityType(VisibilityType visibilityType) {
        this.visibilityType = visibilityType;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public List<MediaUploads> getMediaUploads() {
        return mediaUploads;
    }

    public void setMediaUploads(List<MediaUploads> mediaUploads) {
        this.mediaUploads = mediaUploads;
    }

    public List<ProjectTech> getTechList() {
        return techList;
    }

    public void setTechList(List<ProjectTech> techList) {
        this.techList = techList;
    }
}
