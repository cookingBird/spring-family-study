package com.example.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class DataSourceDemoApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DataSourceDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showDatasource();
        showConnection();
        showData();
    }

    public void showDatasource() {
        System.out.println("show dataSource --------------------------");
        log.info(dataSource.toString());
    }

    public void showConnection() throws SQLException {
        System.out.println("show connection --------------------------");
        Connection cnn = dataSource.getConnection();
        log.info(cnn.toString());
        cnn.close();
    }

    public void showData() {
        System.out.println("show data --------------------------");
        jdbcTemplate.queryForList("SELECT * FROM FOO")
                .forEach(row -> log.info(row.toString()));
    }

}

