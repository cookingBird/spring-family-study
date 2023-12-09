package com.example.multidatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class
})
@Slf4j
public class MultiDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties fooDatasourceProp() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDatasource() {
        DataSourceProperties fooDSP = fooDatasourceProp();
        log.info("foo datasource: {}", fooDSP.getUrl());
        return fooDSP.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager fooTxM() {
        return new DataSourceTransactionManager(fooDatasource());
    }

    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDatasourceProp() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDatasource() {
        DataSourceProperties barDsP = barDatasourceProp();
        log.info("bar datasource: {}", barDsP.getUrl());
        return barDsP.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager barTxM() {
        return new DataSourceTransactionManager(barDatasource());
    }
}
