package com.qrsender.util.configuration;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:util.properties")
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    public String encryptDecryptKey;

    @Bean
    public TextEncryptor createTextDecryptor(){
        BasicTextEncryptor textDecryptor = new BasicTextEncryptor();
        textDecryptor.setPassword(encryptDecryptKey);
        return textDecryptor;
    }
}
