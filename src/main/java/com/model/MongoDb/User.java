package com.model.MongoDb;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;
    @Size(min = 3, message = "Minimum length of username should be 3")
    @NotBlank(message = "Username is mandatory")
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(
        regexp = "^(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+]{6,}$",
        message = "Password must be at least 6 characters long, contain at least one number, and include only letters, numbers, and special characters (!@#$%^&*()_+)."
    )
    private String password;

    private List<UserEducation> educationList;
    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserEducation> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<UserEducation> educationList) {
        this.educationList = educationList;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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
