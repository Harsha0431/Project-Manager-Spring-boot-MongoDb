package com.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.controller", "com.manager.config", "com.service", "com.repository"})
@EnableMongoRepositories(basePackages = {"com.repository"})
public class ProjectManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApplication.class, args);
	}
}
