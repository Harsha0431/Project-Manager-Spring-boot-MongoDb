package com.model.MongoDb;

public class UserDetails {
    private String name;
    private String secondaryEmail;
    private String linkedInProfile;
    private String portfolioLink;
    private String githubLink;

    public UserDetails(){}

    public UserDetails(String name, String secondaryEmail, String linkedInProfile, String portfolioLink, String githubLink){
        this.githubLink = githubLink;
        this.name = name;
        this.linkedInProfile = linkedInProfile;
        this.portfolioLink = portfolioLink;
        this.secondaryEmail = secondaryEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public String getPortfolioLink() {
        return portfolioLink;
    }

    public void setPortfolioLink(String portfolioLink) {
        this.portfolioLink = portfolioLink;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
}
