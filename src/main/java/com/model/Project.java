package com.model;

import com.Helpers.ProjectStatus;
import com.Helpers.VisibilityType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "project")
@CompoundIndexes({
        @CompoundIndex(name = "project_user_unique", def = "{'userEmail': 1, 'title': 1}", unique = true),
        @CompoundIndex(name = "project_user_media", def = "{'mediaUploads.mediaLink': 1, 'userEmail': 1, 'title': 1}")
})
public class Project {
    @Id
    private ObjectId id;
    private String userEmail;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private VisibilityType visibilityType;
    @NotNull(message = "Visibility type is required")
    @Transient
    private String visibility;
    private ProjectStatus projectStatus;
    @NotNull(message = "Status is required")
    @Transient
    private String status;
    private long likeCount;
    private String previewLink;
    private String githubLink;

    private List<MediaUploads> mediaUploads;
    private List<String> techList;

    public Project(){
        this.projectStatus = ProjectStatus.INITIATION;
        this.visibilityType = VisibilityType.PUBLIC;
        this.status = "INITIATION";
        this.visibility = "public";
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public List<String> getTechList() {
        return techList;
    }

    public void setTechList(List<String> techList) {
        this.techList = techList;
    }
}
