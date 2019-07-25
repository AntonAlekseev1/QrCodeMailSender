package com.qrsender.controller.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.util.StringUtils;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableScheduling
@ComponentScan({"com.qrsender.dal", "com.qrsender.service", "com.qrsender.controller"})
public class Application {

    /**
     * The variables "JASYPT_KEY" & "JDBC_DATABASE_URL" must be set in system environments
     */
    public static void main(String[] args) throws Exception{
        String databaseUrl = System.getenv("JDBC_DATABASE_URL");
        String jasyptKey = System.getenv("JASYPT_KEY");
        if (StringUtils.isEmptyOrWhitespace(databaseUrl)) {
            throw new Exception("not set system environment variable JDBC_DATABASE_URL");
        }
        if (StringUtils.isEmptyOrWhitespace(jasyptKey)) {
            throw new Exception("not set system environment variable JASYPT_KEY");
        }
        System.setProperty("spring.datasource.url", databaseUrl);
        System.setProperty("jasypt.encryptor.password", jasyptKey);
        SpringApplication.run(Application.class, args);
    }
}
