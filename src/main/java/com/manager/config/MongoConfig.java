package com.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public boolean autoIndexCreation() {
          return true;
      }

    @Override
    protected String getDatabaseName() {
        return "ProjectManagerSpring";
    }
}
