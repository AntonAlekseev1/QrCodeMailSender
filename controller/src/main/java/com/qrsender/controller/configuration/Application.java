package com.qrsender.controller.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan({"com.qrsender.dal", "com.qrsender.service", "com.qrsender.controller"})
public class Application {

    /**
     * The variables "JASYPT_KEY" & "JDBC_DATABASE_URL" must be set in system environments
     */
    public static void main(String[] args) {
        System.setProperty("spring.datasource.url", System.getenv("JDBC_DATABASE_URL"));
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_KEY"));
        SpringApplication.run(Application.class, args);
    }
}
