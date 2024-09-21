package com.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.controller", "com.manager.config", "com.service", "com.repository"})
@EnableMongoRepositories(basePackages = {"com.repository"})
public class ProjectManagerApplication {
	@Value("${ALLOWED_ORIGINS}")
	String[] allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:5173")
						.allowedHeaders("*", "Authorization", "Content-Type")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowCredentials(true)
						.exposedHeaders("Authorization")
						.maxAge(3600);
			}
		};
	}
}
