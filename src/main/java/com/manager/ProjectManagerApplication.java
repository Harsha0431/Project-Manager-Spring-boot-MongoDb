package com.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication() // exclude = {DataSourceAutoConfiguration.class}
@ComponentScan(basePackages = {"com.controller", "com.manager.config", "com.service", "com.repository"})
@EntityScan(basePackages = {"com.model"})
@EnableJpaRepositories(basePackages = {"com.repository"})
@EnableMongoRepositories(basePackages = {"com.repository.MongoDb"})
public class ProjectManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApplication.class, args);
	}
}
