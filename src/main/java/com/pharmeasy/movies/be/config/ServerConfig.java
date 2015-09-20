package com.pharmeasy.movies.be.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.pharmeasy.movies.db.MongoDBManager;

@Configuration
@PropertySource({ "classpath:app.properties" })
public class ServerConfig {

    private static Logger      logger = LoggerFactory.getLogger(ServerConfig.class);

    @Autowired
    private Environment        env;

    @Autowired
    private ApplicationContext applicationContext;

   

    @Bean
   public MongoDBManager mongoDBManager(){
    	MongoDBManager mdm = new MongoDBManager(env.getProperty("mongo.host"), env.getProperty("mongo.db.movies"));
    	return mdm;
    }

}
