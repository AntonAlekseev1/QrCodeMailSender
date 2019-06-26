package com.qrsender.controller.configuration.profilies;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("profileProperties")
@Profile("heroku")
@PropertySource({"classpath:heroku/database.properties", "classpath:heroku/application.properties", "classpath:heroku/qrCodeApp.properties"})
public class HerokuConfiguration {

}
