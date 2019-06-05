package com.qrsender.dal.configuration;

import com.qrsender.util.EncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.qrsender.util")
public class DaoConfig {

    private final EncryptorUtil encryptor;
    @Value("${database.driverClassName}")
    private String driverClassName;
    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.enable_lazy_load_no_trans}")
    private String hibernateLazyLoadNoTrans;
    @Value("${hibernate.use_jdbc_metadata_defaults}")
    private String hibernateTempUseJdbcMetadataDefaults;

    @Autowired
    public DaoConfig(EncryptorUtil encryptor) {
        this.encryptor = encryptor;
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(databaseUrl, username, encryptor.decrypt(password));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("com.qrsender.model");
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setJpaProperties(getJpaProperties());
        return entityManager;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("show_sql", showSql);
        properties.setProperty("hibernate.enable_lazy_load_no_trans", hibernateLazyLoadNoTrans);
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", hibernateTempUseJdbcMetadataDefaults);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager);
        return transactionManager;
    }
}