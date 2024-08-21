package com.model.MongoDb;

import com.Helpers.EducationType;

import java.time.LocalDate;


public class UserEducation {
    private EducationType educationType;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String course;

    public UserEducation(){}

    public UserEducation(EducationType educationType, String institutionName, LocalDate startDate, LocalDate endDate, String course){
        setEducationType(educationType);
        setCourse(course);
        setInstitutionName(institutionName);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
