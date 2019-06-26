package com.qrsender.controller.configuration.profilies;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("profileProperties")
@Profile("local")
@PropertySource({"classpath:database.properties", "classpath:application.properties", "classpath:qrCodeApp.properties"})
public class LocalConfiguration {

}
