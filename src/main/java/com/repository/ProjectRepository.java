package com.repository;

import com.model.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    List<Project> findByUserEmail(String email);
}
